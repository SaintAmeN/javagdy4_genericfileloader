package com.sda.javagdy4.genericquestionnaire;

import com.sda.javagdy4.genericquestionnaire.model.Question;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileLoader<Question> fileLoader = new FileLoader<>(Question.class);
        Map<Long, Question> questionMap = fileLoader.load();
    }
}
