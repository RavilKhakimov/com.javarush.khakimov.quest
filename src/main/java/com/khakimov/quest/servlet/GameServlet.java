package com.khakimov.quest.servlet;

import com.khakimov.quest.model.GameSessionState;
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
        PlayerState playerState = gameService.getOrCreatePlayerState(session);
        int currentSceneId = gameService.getCurrentSceneId(session);

        // Используем сервис для получения сцены
        Question currentScene = gameService.getFilteredScene(currentSceneId, playerState);

        request.setAttribute("scene", currentScene);
        request.setAttribute("playerState", playerState);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        PlayerState playerState = gameService.getOrCreatePlayerState(session);
        int currentSceneId = gameService.getCurrentSceneId(session);

        GameSessionState gameState;

        // 1. Обработка ввода имени
        String playerName = request.getParameter("playerName");
        if (playerName != null && !playerName.trim().isEmpty()) {
            gameState = gameService.handlePlayerName(playerName, playerState);
        }
        // 2. Обработка выбора действия
        else if (request.getParameter("actionId") != null) {
            String actionIdParam = request.getParameter("actionId");
            gameState = gameService.handleGameAction(actionIdParam, currentSceneId, playerState, session);
        }
        // 3. Обработка перезапуска
        else if (request.getParameter("restart") != null) {
            gameState = gameService.handleRestart(playerState);
        }
        // 4. Если ничего не выбрано - остаемся на текущей сцене
        else {
            gameState = new GameSessionState(playerState, currentSceneId, null);
        }

        // Обновляем сессию через сервис
        gameService.updateSession(session, gameState);

        response.sendRedirect("game");
    }
}