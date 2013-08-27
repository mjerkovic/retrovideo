package com.mlj.retrovideo.account.domain;

import com.mlj.retrovideo.account.AccountView;
import com.mlj.retrovideo.account.command.CreateAccount;
import com.mlj.retrovideo.account.repository.ElasticAccountRepository;
import com.mlj.retrovideo.repository.ItemList;
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

    public ItemList<AccountView> accountsForPage(int pageNo) {
        return repository.accountsForPage(pageNo);
    }

    public AccountView getAccount(String accountNo) {
        return repository.getAccount(accountNo);
    }

}
