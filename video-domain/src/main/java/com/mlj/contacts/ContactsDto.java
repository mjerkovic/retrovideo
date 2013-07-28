package com.mlj.contacts;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;

public class ContactsDto {

    private final List<Contact> contacts;

    public ContactsDto(Contact... contacts) {
        this.contacts = Arrays.asList(contacts);
    }

    public List<Contact> getContacts() {
        return copyOf(contacts);
    }

}
