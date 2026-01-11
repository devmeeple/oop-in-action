import generic.Money;
import reservation.domain.*;
import reservation.persistence.*;
import reservation.persistence.memory.*;
import reservation.service.ReservationService;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.LocalTime.of;
import static reservation.domain.DiscountCondition.ConditionType.PERIOD_CONDITION;
import static reservation.domain.DiscountCondition.ConditionType.SEQUENCE_CONDITION;
import static reservation.domain.DiscountPolicy.PolicyType.AMOUNT_POLICY;

public class Main {

    private ScreeningDAO screeningDAO = new ScreeningMemoryDAO();
    private MovieDAO movieDAO = new MovieMemoryDAO();
    private DiscountConditionDAO discountConditionDAO = new DiscountConditionMemoryDAO();
    private DiscountPolicyDAO discoutPolicyDAO = new DiscountPolicyMemoryDAO(discountConditionDAO);
    private ReservationDAO reservationDAO = new ReservationMemoryDAO();

    ReservationService reservationService = new ReservationService(
            screeningDAO,
            movieDAO,
            discoutPolicyDAO,
            discountConditionDAO,
            reservationDAO
    );

    private Screening initializeData() {
        Movie movie = new Movie("한산", 150, Money.wons(10000));
        movieDAO.insert(movie);

        DiscountPolicy discountPolicy = new DiscountPolicy(
                movie.getId(),
                AMOUNT_POLICY,
                Money.wons(1000),
                null
        );
        discoutPolicyDAO.insert(discountPolicy);

        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), SEQUENCE_CONDITION, null, null, null, 1));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), SEQUENCE_CONDITION, null, null, null, 10));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), PERIOD_CONDITION, MONDAY, of(10, 0), of(12, 0), null));
        discountConditionDAO.insert(new DiscountCondition(discountPolicy.getId(), PERIOD_CONDITION, WEDNESDAY, of(18, 0), of(21, 0), null));

        Screening screening = new Screening(movie.getId(), 7, LocalDateTime.of(2024, 12, 11, 18, 0));
        screeningDAO.insert(screening);

        return screening;
    }

    public void run() {
        Screening screening = initializeData();

        Reservation reservation = reservationService.reserveScreening(1L, screening.getId(), 2);

        System.out.printf("관객수 : %d, 요금: %s%n", reservation.getAudienceCount(), reservation.getFee());
    }

    static void main() {
        new Main().run();
    }
}
