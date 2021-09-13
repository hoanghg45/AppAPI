package com.example.appapi.Models;

public class User {
    public String MSSV;
    public String FullName;
    public String Email;
    public String Phone;

    public User(String MSSV, String fullName, String email, String phone) {
        this.MSSV = MSSV;
        FullName = fullName;
        Email = email;
        Phone = phone;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
