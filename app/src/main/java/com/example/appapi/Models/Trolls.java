package com.example.appapi.Models;

public class Trolls {
    private Q1 q1;
    private Q2 q2;
    private Q3 q3;
    private Q4 q4;

    public Trolls(Q1 q1, Q2 q2, Q3 q3, Q4 q4) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
    }

    public Q1 getQ1() {
        return q1;
    }

    public void setQ1(Q1 q1) {
        this.q1 = q1;
    }

    public Q2 getQ2() {
        return q2;
    }

    public void setQ2(Q2 q2) {
        this.q2 = q2;
    }

    public Q3 getQ3() {
        return q3;
    }

    public void setQ3(Q3 q3) {
        this.q3 = q3;
    }

    public Q4 getQ4() {
        return q4;
    }

    public void setQ4(Q4 q4) {
        this.q4 = q4;
    }
}
