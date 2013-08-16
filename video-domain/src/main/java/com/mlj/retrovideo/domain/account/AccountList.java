package com.mlj.retrovideo.domain.account;

import java.util.List;

public class AccountList {

    private final int page;
    private final long totalPages;
    private final List<AccountView> accounts;

    public AccountList(int page, long totalAccounts, List<AccountView> accounts) {
        this.page = page;
        this.totalPages = Math.round(Math.ceil((double) totalAccounts / 10));
        this.accounts = accounts;
    }

    public int getPage() {
        return page;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public List<AccountView> getAccounts() {
        return accounts;
    }

}
