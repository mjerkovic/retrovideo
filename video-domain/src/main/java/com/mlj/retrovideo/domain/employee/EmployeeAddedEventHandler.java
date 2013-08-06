package com.mlj.retrovideo.domain.employee;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAddedEventHandler {

    @EventHandler
    public void handle(EmployeeAdded event, JdbcEmployeeWriteRepository jdbcRepository, EmployeeWriteRepository repository) {
        jdbcRepository.addEmployee(event);
        repository.addEmployee(event);
    }

}
