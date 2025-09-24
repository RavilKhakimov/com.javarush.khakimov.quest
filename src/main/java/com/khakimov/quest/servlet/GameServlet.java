package com.khakimov.quest.servlet;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.GameAction;
import com.khakimov.quest.model.results.GameResult;
import com.khakimov.quest.storage.GameStorage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        // Получаем или создаем состояние игрока
        PlayerState playerState = (PlayerState) session.getAttribute("playerState");
        if (playerState == null) {
            playerState = new PlayerState();
            session.setAttribute("playerState", playerState);
        }

        // Получаем текущую сцену
        Integer currentSceneId = (Integer) session.getAttribute("currentSceneId");
        if (currentSceneId == null) {
            currentSceneId = 0;
            session.setAttribute("currentSceneId", currentSceneId);
        }

        // Получаем сцену из хранилища
        Question currentScene = GameStorage.scenes.get(currentSceneId);
        if (currentScene == null) {
            currentScene = GameStorage.scenes.get(0); // Fallback to prologue
        }

        // Устанавливаем атрибуты для JSP
        request.setAttribute("scene", currentScene);
        request.setAttribute("playerState", playerState);

        // Перенаправляем на JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        PlayerState playerState = (PlayerState) session.getAttribute("playerState");

        if (playerState == null) {
            playerState = new PlayerState();
            session.setAttribute("playerState", playerState);
        }

        // Обработка ввода имени
        String playerName = request.getParameter("playerName");
        if (playerName != null && !playerName.trim().isEmpty()) {
            playerState.setName(playerName.trim());
            session.setAttribute("currentSceneId", 1); // Переход к сцене 1
        }

        // Обработка перезапуска
        if (request.getParameter("restart") != null) {
            session.invalidate();
        }

        response.sendRedirect("game");
    }
}