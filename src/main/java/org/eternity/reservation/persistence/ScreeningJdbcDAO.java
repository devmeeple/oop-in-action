package org.eternity.reservation.persistence;

import org.eternity.generic.Money;
import org.eternity.reservation.domain.*;
import org.eternity.reservation.service.ScreeningDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public class ScreeningJdbcDAO implements ScreeningDAO {

    private JdbcClient jdbcClient;

    public ScreeningJdbcDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Screening find(Long id) {
        return queryScreening(id);
    }

    private Screening queryScreening(Long screeningId) {
        return jdbcClient.sql(
                "SELECT id, movie_id, sequence, when_screened " +
                "FROM screening " +
                "WHERE id = :id")
                .param("id", screeningId)
                .query((rs, rowNum) -> new Screening(
                        rs.getLong("id"),
                        queryMovie(rs.getLong("movie_id")),
                        rs.getInt("sequence"),
                        rs.getTimestamp("when_screened").toLocalDateTime()))
                .single();
    }

    private Movie queryMovie(Long movieId) {
        return jdbcClient.sql(
                "SELECT id, title, fee " +
                "FROM movie " +
                "WHERE id = :id")
                .param("id", movieId)
                .query((rs, rowNum) -> new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        Money.wons(rs.getInt("fee")),
                        queryDiscountPolicy(movieId)))
                .single();
    }

    private DiscountPolicy queryDiscountPolicy(Long movieId) {
        List<DiscountPolicy> policies = jdbcClient.sql(
                "SELECT id, policy_type, amount, percent " +
                "FROM discount_policy " +
                "WHERE movie_id = :movieId")
                .param("movieId", movieId)
                .query((rs, rowNum) -> {
                    if (rs.getString("policy_type").equals("AMOUNT")) {
                        return new AmountDiscountPolicy(
                                rs.getLong("id"),
                                Money.wons(rs.getInt("amount")),
                                queryDiscountConditions(rs.getLong("id")));
                    }

                    return new PercentDiscountPolicy(
                            rs.getLong("id"),
                            rs.getDouble("percent"),
                            queryDiscountConditions(rs.getLong("id")));
                })
                .list();

        if (policies.size() > 1) {
            return new OverlappedDiscountPolicy(policies.toArray(new DiscountPolicy[0]));
        }

        return policies.isEmpty() ? null : policies.get(0);
    }

    private DiscountCondition[] queryDiscountConditions(Long policyId) {
        return jdbcClient.sql(
                "SELECT id, condition_type, sequence, day_of_week, start_time, end_time " +
                "FROM discount_condition " +
                "WHERE policy_id = :policyId")
                .param("policyId", policyId)
                .query((rs, rowNum) -> {
                    if (rs.getString("condition_type").equals("SEQUENCE")) {
                        return new SequenceCondition(
                                rs.getLong("id"),
                                rs.getInt("sequence"));
                    }

                    return new PeriodCondition(
                            rs.getLong("id"),
                            DayOfWeek.valueOf(rs.getString("day_of_week")),
                            rs.getTime("start_time").toLocalTime(),
                            rs.getTime("end_time").toLocalTime());
                })
                .list().toArray(new DiscountCondition[0]);
    }
}
