package com.example.appapi.Models;

public class Login {
    private int status;
    private String notification;
    private String fullName;
    private String mssv;

    public Login(int status, String notification, String fullName, String mssv) {
        this.status = status;
        this.notification = notification;
        this.fullName = fullName;
        this.mssv = mssv;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }
}
