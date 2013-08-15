package com.mlj.retrovideo.domain.account;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class AccountIndexingEventHandler {

    @EventHandler
    public void handle(AccountCreated event, ElasticAccountRepository repository) {
        repository.createAccount(event);
    }

}
