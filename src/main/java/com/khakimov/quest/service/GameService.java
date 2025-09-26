package com.khakimov.quest.service;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.GameAction;
import com.khakimov.quest.storage.GameStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {
    private Random random = new Random();

    public Question getFilteredScene(int sceneId, PlayerState playerState) {
        Question originalScene = GameStorage.scenes.get(sceneId);
        if (originalScene == null) return null;

        // Фильтруем действия based на состоянии игрока
        List<GameAction> filteredActions = filterActions(originalScene.getActions(), playerState);

        return new Question(originalScene.getId(), originalScene.getText(), filteredActions);
    }

    private List<GameAction> filterActions(List<GameAction> actions, PlayerState playerState) {
        List<GameAction> filtered = new ArrayList<>();

        for (GameAction action : actions) {
            if (isActionAvailable(action, playerState)) {
                filtered.add(action);
            }
        }
        return filtered;
    }

    private boolean isActionAvailable(GameAction action, PlayerState playerState) {
        int actionId = action.getActionId();

        // Действие "Взять вилы" доступно только если вил еще нет
        if (actionId == 13 && playerState.hasFlag("hasPitchfork")) {
            return false;
        }

        // Действие "Искать грибы" доступно только если найдено меньше 3 грибов
        if (actionId == 32 && playerState.getItemCount("грибы") >= 3) {
            return false;
        }

        // другие флаги

        return true;
    }

    // Метод для проверки вероятности (20% шанс найти гриб)
    public boolean checkProbability(int percent) {
        return random.nextInt(100) < percent;
    }

    // Метод для обработки поиска грибов
    public String searchForMushrooms(PlayerState playerState) {
        if (playerState.getItemCount("грибы") >= 3) {
            return "Вы уже нашли достаточно грибов.";
        }

        if (checkProbability(20)) {
            playerState.addItem("грибы");
            int remaining = 3 - playerState.getItemCount("грибы");
            return "Вы нашли гриб! Осталось найти еще " + remaining + " грибов.";
        } else {
            return "Вы ничего не нашли. Попробуйте еще раз.";
        }
    }
}