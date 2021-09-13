package com.example.appapi.Models;

public class Info {
    private int status;
    private Account account;

    public Info(int status, Account account) {
        this.status = status;
        this.account = account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
