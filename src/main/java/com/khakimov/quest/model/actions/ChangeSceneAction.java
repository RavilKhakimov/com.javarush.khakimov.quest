package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

// Простое действие - переход между сценами
public class ChangeSceneAction extends GameAction {
    private int targetSceneId;

    public ChangeSceneAction(int actionId, String text, int targetSceneId) {
        super(actionId, text);
        this.targetSceneId = targetSceneId;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        return new GameResult(targetSceneId, "");
    }
}
