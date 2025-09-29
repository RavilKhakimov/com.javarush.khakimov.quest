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

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<GameAction> getActions() {
        return actions;
    }

    public void setActions(List<GameAction> actions) {
        this.actions = actions;
    }
}
