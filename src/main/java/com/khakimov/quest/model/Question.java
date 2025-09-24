package com.khakimov.quest.model;

import com.khakimov.quest.model.actions.GameAction;

import java.util.List;

public class Question {
    private int id;
    private String text;
    private List<GameAction> actions;

    public Question(int id, String text, List<GameAction> actions) {
        this.id = id;
        this.text = text;
        this.actions = actions;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<GameAction> getActions() {
        return actions;
    }

    // Сеттеры (если нужны)
    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setActions(List<GameAction> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", actions=" + actions +
                '}';
    }
}
