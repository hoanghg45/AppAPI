package com.example.appapi.Models;

public class Score {
    private String mssv;
    private int score;

    public Score(String mssv, int score) {
        this.mssv = mssv;
        this.score = score;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
