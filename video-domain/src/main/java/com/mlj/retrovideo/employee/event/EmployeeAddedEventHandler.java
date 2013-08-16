package com.mlj.retrovideo.employee.event;

import com.mlj.retrovideo.employee.repository.EmployeeWriteRepository;
import com.mlj.retrovideo.employee.repository.JdbcEmployeeWriteRepository;
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
