package com.mlj.retrovideo.domain.employee;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AddEmployee {

    @TargetAggregateIdentifier
    private final String employeeId;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final boolean administrator;

    public AddEmployee(String employeeId, String firstName, String lastName, String username, String password,
                       boolean administrator) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.administrator = administrator;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdministrator() {
        return administrator;
    }
}
