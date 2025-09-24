package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

// Действие - установка флага
public class SetFlagAction extends GameAction {
    private int targetSceneId;
    private String flagName;

    public SetFlagAction(int actionId, String text, String flagName, int targetSceneId) {
        super(actionId, text);
        this.targetSceneId = targetSceneId;
        this.flagName = flagName;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        playerState.setFlag(flagName);
        return new GameResult(targetSceneId, "");
    }
}

