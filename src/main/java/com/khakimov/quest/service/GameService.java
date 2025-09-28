package com.khakimov.quest.service;

import com.khakimov.quest.model.GameSessionState;
import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.GameAction;
import com.khakimov.quest.model.actions.SearchMushroomsAction;
import com.khakimov.quest.model.results.GameResult;
import com.khakimov.quest.storage.GameStorage;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameService {

    public Question getFilteredScene(int sceneId, PlayerState playerState) {
        Question scene = GameStorage.scenes.get(sceneId);
        if (scene == null) {
            return GameStorage.scenes.get(0); // Fallback to prologue
        }

        // Здесь можно добавить фильтрацию действий на основе состояния игрока
        return scene;
    }

    public GameSessionState handleGameAction(String actionIdParam, int currentSceneId,
                                             PlayerState playerState, HttpSession session) {
        if (actionIdParam == null || actionIdParam.trim().isEmpty()) {
            return new GameSessionState(playerState, currentSceneId, null);
        }

        try {
            int actionId = Integer.parseInt(actionIdParam);
            Question currentScene = GameStorage.scenes.get(currentSceneId);

            if (currentScene == null) {
                return new GameSessionState(playerState, 0, "Сцена не найдена, начинаем заново.");
            }

            Optional<GameAction> selectedAction = findActionById(currentScene, actionId);
            if (selectedAction.isEmpty()) {
                return new GameSessionState(playerState, currentSceneId, "Действие не найдено.");
            }

            return executeGameAction(selectedAction.get(), playerState, currentSceneId);

        } catch (NumberFormatException e) {
            System.err.println("Invalid actionId: " + actionIdParam);
            return new GameSessionState(playerState, currentSceneId, "Неверный ID действия.");
        }
    }

    private Optional<GameAction> findActionById(Question scene, int actionId) {
        return scene.getActions().stream()
                .filter(action -> action.getActionId() == actionId)
                .findFirst();
    }

    private GameSessionState executeGameAction(GameAction action, PlayerState playerState,
                                               int currentSceneId) {
        String message = null;
        int nextSceneId = currentSceneId;

        // ОСОБАЯ ОБРАБОТКА для грибов
        if (action instanceof SearchMushroomsAction) {
            message = searchForMushrooms(playerState);
            nextSceneId = currentSceneId; // Остаемся на текущей сцене
        }
        // ОБЫЧНЫЕ действия
        else {
            GameResult result = action.execute(playerState);
            nextSceneId = result.getNextSceneId();
            message = result.getMessage();
        }

        return new GameSessionState(playerState, nextSceneId, message);
    }

    public String searchForMushrooms(PlayerState playerState) {
        // Ваша существующая логика поиска грибов
        // ...
        return "Вы нашли грибы!";
    }

    public GameSessionState handlePlayerName(String playerName, PlayerState playerState) {
        playerState.setName(playerName.trim());
        return new GameSessionState(playerState, 1, "Добро пожаловать, " + playerName + "!");
    }

    public GameSessionState handleRestart(PlayerState playerState) {
        playerState.getFlags().clear();
        playerState.getInventory().clear();
        playerState.setName(null);
        playerState.incrementGamesPlayed();

        return new GameSessionState(playerState, 0, "Игра перезапущена!");
    }

    // Вспомогательные методы для работы с сессией
    public PlayerState getOrCreatePlayerState(HttpSession session) {
        PlayerState playerState = (PlayerState) session.getAttribute("playerState");
        if (playerState == null) {
            playerState = new PlayerState();
            session.setAttribute("playerState", playerState);
        }
        return playerState;
    }

    public int getCurrentSceneId(HttpSession session) {
        Integer currentSceneId = (Integer) session.getAttribute("currentSceneId");
        if (currentSceneId == null) {
            currentSceneId = 0;
            session.getAttribute("currentSceneId");
        }
        return currentSceneId;
    }

    public void updateSession(HttpSession session, GameSessionState gameState) {
        session.setAttribute("currentSceneId", gameState.getCurrentSceneId());
        if (gameState.getActionMessage() != null) {
            session.setAttribute("actionMessage", gameState.getActionMessage());
        }
        // PlayerState уже обновлен по ссылке
    }
}