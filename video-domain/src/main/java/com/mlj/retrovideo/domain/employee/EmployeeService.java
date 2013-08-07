package com.mlj.retrovideo.domain.employee;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final CommandGateway commandGateway;
    private final EmployeeReadRepository repository;

    @Autowired
    public EmployeeService(CommandGateway commandGateway, EmployeeReadRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    public void addEmployee(AddEmployee employee) {
        commandGateway.send(employee);
    }

    public EmployeeView authenticate(String username, String password) {
        return repository.authenticate(username, password);
    }

    public EmployeeView getEmployee(String employeeId) {
        return repository.getEmployee(employeeId);
    }

    public EmployeeView getEmployeeByUsername(String username) {
        return repository.getEmployeeByUsername(username);
    }

}
