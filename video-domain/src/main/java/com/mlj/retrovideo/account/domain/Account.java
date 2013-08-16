package com.mlj.retrovideo.account.domain;

import com.mlj.retrovideo.account.command.CreateAccount;
import com.mlj.retrovideo.account.event.AccountCreated;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class Account extends AbstractAnnotatedAggregateRoot {

    @AggregateIdentifier
    private String accountNo;
    private String title;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String street;
    private String city;
    private String postcode;

    public Account() {

    }

    @CommandHandler
    public Account(CreateAccount command) {
        apply(new AccountCreated(command.getAccountNo(), command.getTitle(), command.getFirstName(), command.getLastName(),
                command.getDateOfBirth(), command.getStreet(), command.getCity(), command.getPostcode()));
    }

    @EventHandler
    public void on(AccountCreated event) {
        this.accountNo = event.getAccountNo();
        this.title = event.getTitle();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.dateOfBirth = event.getDateOfBirth();
        this.street = event.getStreet();
        this.city = event.getCity();
        this.postcode = event.getPostcode();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }
}
