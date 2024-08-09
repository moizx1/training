package com.redmath.lecture04.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDataService {

    private final JdbcTemplate jdbcTemplate;

    public UserDataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUserName(String username) {
        return jdbcTemplate.queryForObject("select * from users where username = ?", User.class, username);
    }
}
