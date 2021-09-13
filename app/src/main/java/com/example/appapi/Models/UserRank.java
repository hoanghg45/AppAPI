package com.example.appapi.Models;

public class UserRank {
    private String fullName;
    private int score;

    public UserRank(String fullName, int score) {
        this.fullName = fullName;
        this.score = score;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
