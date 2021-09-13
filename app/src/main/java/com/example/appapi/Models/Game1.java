package com.example.appapi.Models;

public class Game1 {
    private Quiz quiz;

    public Game1(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
