package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

// Действие - добавление предмета в инвентарь
public class AddItemAction extends GameAction {
    private int targetSceneId;
    private String itemName;

    public AddItemAction(int actionId, String text, String itemName, int targetSceneId) {
        super(actionId, text);
        this.targetSceneId = targetSceneId;
        this.itemName = itemName;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        playerState.addItem(itemName);
        return new GameResult(targetSceneId, "Вы получили: " + itemName);
    }
}
