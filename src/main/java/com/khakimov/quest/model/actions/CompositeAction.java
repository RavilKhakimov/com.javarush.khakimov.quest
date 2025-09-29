package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

import java.util.ArrayList;
import java.util.List;

public class CompositeAction extends GameAction {
    private List<GameAction> actions = new ArrayList<>();
    private int nextSceneId;

    public CompositeAction(int actionId, String text, int nextSceneId) {
        super(actionId, text);
        this.nextSceneId = nextSceneId;
    }

    public CompositeAction addAction(GameAction action) {
        actions.add(action);
        return this;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        for (GameAction action : actions) {
            action.execute(playerState);
        }
        return new GameResult(nextSceneId, "");
    }
}
