package com.example.appapi.Models;

public class LoginInput {
    private String MSSV;
    private String Email;

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public LoginInput(String MSSV, String email) {
        this.MSSV = MSSV;
        Email = email;
    }
}
