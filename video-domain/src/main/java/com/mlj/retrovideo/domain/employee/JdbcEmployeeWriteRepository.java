package com.mlj.retrovideo.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEmployeeWriteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcEmployeeWriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addEmployee(EmployeeAdded event) {
        jdbcTemplate.update("insert into users (employeeId, firstName, lastName, userName, password, administrator) " +
                "values (?, ?, ?, ?, ?, ?)", event.getEmployeeId(), event.getFirstName(), event.getLastName(),
                event.getUsername(), event.getPassword(), event.isAdministrator());
    }

}
