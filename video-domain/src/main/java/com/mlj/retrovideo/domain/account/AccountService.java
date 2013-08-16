package com.mlj.retrovideo.domain.account;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final CommandGateway commandGateway;
    private final ElasticAccountRepository repository;

    @Autowired
    public AccountService(CommandGateway commandGateway, ElasticAccountRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    public void createAccount(CreateAccount createAccount) {
        commandGateway.send(createAccount);
    }

    public AccountList accountsForPage(int pageNo) {
        return repository.accountsForPage(pageNo);
    }

}
