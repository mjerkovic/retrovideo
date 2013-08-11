package com.mlj.retrovideo.domain.account;

public class AccountCreated {

    private final String accountNo;
    private final String title;
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String street;
    private final String city;
    private final String postcode;

    public AccountCreated(String accountNo, String title, String firstName, String lastName, String dateOfBirth,
                          String street, String city, String postcode) {
        this.accountNo = accountNo;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
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
