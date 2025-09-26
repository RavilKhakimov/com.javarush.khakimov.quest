package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

import java.util.List;

public class QuizAction extends GameAction {
    private String question;
    private List<String> options;
    private int correctOptionIndex;

    public QuizAction(int actionId, String text, String question, 
                     List<String> options, int correctOptionIndex, int nextSceneId) {
        super(actionId, text);
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    // Геттеры для вопроса и вариантов
    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }

    @Override
    public GameResult execute(PlayerState playerState) {
        return null;
    }
}