package com.mlj.contacts;

public class ContactsService {

    public ContactsDto all() {
        return new ContactsDto(new Contact("Bob", "Smith"), new Contact("Ron", "Jones"));
    }

}
