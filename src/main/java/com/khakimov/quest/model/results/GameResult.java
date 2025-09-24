package com.khakimov.quest.model.results;

// Результат выполнения действия
public class GameResult {
    private int nextSceneId;
    private String message;

    public GameResult(int nextSceneId, String message) {
        this.nextSceneId = nextSceneId;
        this.message = message;
    }

    // Геттеры
    public int getNextSceneId() { return nextSceneId; }
    public String getMessage() { return message; }
}
