package com.khakimov.quest.servlet;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.GameAction;
import com.khakimov.quest.model.actions.SearchMushroomsAction;
import com.khakimov.quest.model.results.GameResult;
import com.khakimov.quest.service.GameService;
import com.khakimov.quest.storage.GameStorage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private GameService gameService;

    @Override
    public void init() throws ServletException {
        this.gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        PlayerState playerState = getOrCreatePlayerState(session);
        int currentSceneId = getCurrentSceneId(session);

        // фильтрованные сцены
        Question currentScene = gameService.getFilteredScene(currentSceneId, playerState);
        if (currentScene == null) {
            currentScene = GameStorage.scenes.get(0);
        }
        // помещает объект в запрос под именем "scene"
        request.setAttribute("scene", currentScene);
        request.setAttribute("playerState", playerState);
        // передает управление и данные JSP-файлу
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        PlayerState playerState = getOrCreatePlayerState(session);
        int currentSceneId = getCurrentSceneId(session);

        // Обработка ввода имени
        String playerName = request.getParameter("playerName");
        if (playerName != null && !playerName.trim().isEmpty()) {
            playerState.setName(playerName.trim());
            session.setAttribute("currentSceneId", 1);
        }

        // Обработка выбора действия
        String actionIdParam = request.getParameter("actionId");
        if (actionIdParam != null) {
            try {
                int actionId = Integer.parseInt(actionIdParam);
                Question currentScene = GameStorage.scenes.get(currentSceneId);

                if (currentScene != null) {
                    GameAction selectedAction = findActionById(currentScene, actionId);

                    if (selectedAction != null) {
                        //для действия поиска грибов
                        if (selectedAction instanceof SearchMushroomsAction) {
                            String message = gameService.searchForMushrooms(playerState);
                            session.setAttribute("actionMessage", message);
                            // Остаемся на текущей сцене (лес)
                            session.setAttribute("currentSceneId", currentSceneId);
                        }
                        // Обычные действия
                        else {
                            GameResult result = selectedAction.execute(playerState);
                            session.setAttribute("currentSceneId", result.getNextSceneId());

                            if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                                session.setAttribute("actionMessage", result.getMessage());
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid actionId: " + actionIdParam);
            }
        }

        // 3. перезапуск
        if (request.getParameter("restart") != null) {
            session.invalidate();
        }

        response.sendRedirect("game");
    }

    private PlayerState getOrCreatePlayerState(HttpSession session) {
        PlayerState playerState = (PlayerState) session.getAttribute("playerState");
        if (playerState == null) {
            playerState = new PlayerState();
            session.setAttribute("playerState", playerState);
        }
        return playerState;
    }

    private int getCurrentSceneId(HttpSession session) {
        Integer currentSceneId = (Integer) session.getAttribute("currentSceneId");
        if (currentSceneId == null) {
            currentSceneId = 0;
            session.setAttribute("currentSceneId", currentSceneId);
        }
        return currentSceneId;
    }

    private GameAction findActionById(Question scene, int actionId) {
        for (GameAction action : scene.getActions()) {
            if (action.getActionId() == actionId) {
                return action;
            }
        }
        return null;
    }
}