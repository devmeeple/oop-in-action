package org.eternity.reservation.persistence;

import org.eternity.reservation.domain.Customer;
import org.eternity.reservation.service.CustomerDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcDAO implements CustomerDAO {

    private JdbcClient jdbcClient;

    public CustomerJdbcDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Customer find(Long id) {
        return jdbcClient.sql("SELECT id, name FROM customer WHERE id = :id")
                .param("id", id)
                .query((rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name")))
                .single();
    }
}
