package com.mlj.retrovideo.web.account;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.UUID;

import com.mlj.retrovideo.account.AccountView;
import com.mlj.retrovideo.account.command.CreateAccount;
import com.mlj.retrovideo.account.domain.AccountService;
import com.mlj.retrovideo.repository.ItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = POST, value = "/account", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountDto account) {
        accountService.createAccount(new CreateAccount(UUID.randomUUID().toString(), capitalizeFully(account.getTitle()),
                capitalizeFully(account.getFirstName()), capitalizeFully(account.getLastName()), account.getDateOfBirth(),
                account.getStreet(), account.getCity(), account.getPostcode()));
    }

    @RequestMapping(method = GET, value = "/account/page/{pageNo}", produces = "application/json")
    @ResponseBody
    public ItemList<AccountView> accountsByPage(@PathVariable int pageNo) {
        return accountService.accountsForPage(pageNo);
    }

    @RequestMapping(method = GET, value = "/account/{accountNo}", produces = "application/json")
    @ResponseBody
    public AccountView getAccount(@PathVariable String accountNo) {
        return accountService.getAccount(accountNo);
    }

}
