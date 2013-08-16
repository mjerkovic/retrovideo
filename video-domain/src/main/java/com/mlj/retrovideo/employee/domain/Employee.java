package com.mlj.retrovideo.employee.domain;

import com.mlj.retrovideo.employee.command.AddEmployee;
import com.mlj.retrovideo.employee.event.EmployeeAdded;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Employee extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String employeeId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean administrator;

    public Employee() {
    }

    @CommandHandler
    public Employee(AddEmployee command) {
        apply(new EmployeeAdded(command.getEmployeeId(), command.getFirstName(), command.getLastName(),
                command.getUsername(), command.getPassword(), command.isAdministrator()));
    }

    @EventHandler
    public void on(EmployeeAdded event) {
        employeeId = event.getEmployeeId();
        firstName = event.getFirstName();
        lastName = event.getLastName();
        username = event.getUsername();
        password = event.getPassword();
        administrator = event.isAdministrator();
    }

}
