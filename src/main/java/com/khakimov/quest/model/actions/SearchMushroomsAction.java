package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

public class SearchMushroomsAction extends GameAction {
    private final int nextSceneId;

    public SearchMushroomsAction(int actionId, String text, int nextSceneId) {
        super(actionId, text);
        this.nextSceneId = nextSceneId;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        return new GameResult(-1, "SEARCH_MUSHROOMS");
    }
}
