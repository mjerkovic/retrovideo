package com.mlj.retrovideo.domain.account;

import java.util.List;

public class AccountList {

    private final int page;
    private final long totalPages;
    private final long totalAccounts;
    private final List<AccountView> accounts;
    private final String searchKey;

    public AccountList(int page, String searchKey, long totalAccounts, List<AccountView> accounts) {
        this.page = page;
        this.totalAccounts = totalAccounts;
        this.totalPages = Math.round(Math.ceil((double) totalAccounts / 10));
        this.accounts = accounts;
        this.searchKey = searchKey;
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

    public String getSearchKey() {
        return searchKey;
    }

    public long getTotalVideos() {
        return totalAccounts;
    }

}
