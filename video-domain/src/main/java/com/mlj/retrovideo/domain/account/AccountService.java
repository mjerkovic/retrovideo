package com.mlj.retrovideo.domain.account;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final CommandGateway commandGateway;

    @Autowired
    public AccountService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void createAccount(CreateAccount createAccount) {
        commandGateway.send(createAccount);
    }

}
