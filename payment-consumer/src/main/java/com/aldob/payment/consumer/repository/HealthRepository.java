package com.aldob.payment.consumer.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HealthRepository {

    private final JdbcTemplate jdbcTemplate;

    public void testConnexion() {
        jdbcTemplate.queryForObject("SELECT 1", Integer.class);
    }

}

