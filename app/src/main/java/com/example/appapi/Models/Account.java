package com.example.appapi.Models;

public class Account {
    private int no;
    private String mssv;
    private String fullName;
    private String email;
    private String phone;
    private String score;

    public Account(int no, String mssv, String fullName, String email, String phone, String score) {
        this.no = no;
        this.mssv = mssv;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.score = score;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
