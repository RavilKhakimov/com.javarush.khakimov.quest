package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;

import java.util.HashMap;
import java.util.Map;

public class RestartGameAction extends GameAction {
    private int nextSceneId;

    public RestartGameAction(int actionId, String text, int nextSceneId) {
        super(actionId, text);
        this.nextSceneId = nextSceneId;
    }

    @Override
    public GameResult execute(PlayerState playerState) {
        playerState.incrementGamesPlayed();

        // Сбрасываем состояние, но сохраняем счетчик
        Map<String, Integer> savedFlags = new HashMap<>();
        int savedGamesPlayed = playerState.getGamesPlayed();

        // Полностью очищаем
        playerState.getFlags().clear();
        playerState.getInventory().clear();
        playerState.setName(null);

        // Восстанавливаем счетчик
        playerState.setGamesPlayed(savedGamesPlayed);

        return new GameResult(nextSceneId,
                "Игра перезапущена! Это ваша " + savedGamesPlayed + " игра.");
    }

    @Override
    public int getActionId() {
        return actionId;
    }

    @Override
    public String getText() {
        return text;
    }
}
