package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

// Базовый класс для всех действий
public abstract class GameAction {
    protected int actionId;
    protected String text;

    public GameAction(int actionId, String text) {
        this.actionId = actionId;
        this.text = text;
    }

    // Главный метод, который выполняет действие
    public abstract GameResult execute(PlayerState playerState);

    // Геттеры
    public int getActionId() { return actionId; }
    public String getText() { return text; }
}