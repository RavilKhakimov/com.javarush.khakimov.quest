package com.khakimov.quest.model;

public class GameSessionState {
    private final PlayerState playerState;
    private final int currentSceneId;
    private final String actionMessage;

    public GameSessionState(PlayerState playerState, int currentSceneId, String actionMessage) {
        this.playerState = playerState;
        this.currentSceneId = currentSceneId;
        this.actionMessage = actionMessage;
    }

    public PlayerState getPlayerState() { return playerState; }
    public int getCurrentSceneId() { return currentSceneId; }
    public String getActionMessage() { return actionMessage; }
}
