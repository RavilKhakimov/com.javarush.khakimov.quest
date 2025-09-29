// src/test/java/com/khakimov/quest/service/GameServiceTest.java
package com.khakimov.quest.service;

import com.khakimov.quest.model.GameSessionState;
import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.Question;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void shouldHandlePlayerNameCorrectly() {
        // Given
        PlayerState playerState = new PlayerState();
        String playerName = "Иван";

        // When
        GameSessionState result = gameService.handlePlayerName(playerName, playerState);

        // Then
        assertEquals("Иван", playerState.getName());
        assertEquals(1, result.getCurrentSceneId());
        assertEquals("Добро пожаловать, Иван!", result.getActionMessage());
        assertEquals(playerState, result.getPlayerState());
    }

    @Test
    void shouldHandleRestartGame() {
        // Given
        PlayerState playerState = new PlayerState();
        playerState.setName("Иван");
        playerState.setFlag("hasPitchfork");
        playerState.addItem("вилы");

        // When
        GameSessionState result = gameService.handleRestart(playerState);

        // Then
        assertFalse(playerState.hasFlag("hasPitchfork"));
        assertFalse(playerState.hasItem("вилы"));
        assertNull(playerState.getName());
        assertEquals(2, playerState.getGamesPlayed());

        assertEquals(0, result.getCurrentSceneId());
        assertEquals("Игра перезапущена!", result.getActionMessage());
    }

    @Test
    void shouldGetOrCreatePlayerStateWhenNotExists() {
        // Given
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        PlayerState result = gameService.getOrCreatePlayerState(session);

        // Then
        assertNotNull(result);
        assertNotNull(session.getAttribute("playerState"));
    }

    @Test
    void shouldGetExistingPlayerState() {
        // Given
        SimpleHttpSession session = new SimpleHttpSession();
        PlayerState existingPlayer = new PlayerState();
        session.setAttribute("playerState", existingPlayer);

        // When
        PlayerState result = gameService.getOrCreatePlayerState(session);

        // Then
        assertEquals(existingPlayer, result);
    }

    @Test
    void shouldGetCurrentSceneIdWhenNotSet() {
        // Given
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        int result = gameService.getCurrentSceneId(session);

        // Then
        assertEquals(0, result);
        assertEquals(0, session.getAttribute("currentSceneId"));
    }

    @Test
    void shouldGetExistingCurrentSceneId() {
        // Given
        SimpleHttpSession session = new SimpleHttpSession();
        session.setAttribute("currentSceneId", 5);

        // When
        int result = gameService.getCurrentSceneId(session);

        // Then
        assertEquals(5, result);
    }

    @Test
    void shouldHandleGameActionWithInvalidId() {
        // Given
        PlayerState playerState = new PlayerState();
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        GameSessionState result = gameService.handleGameAction("invalid", 1, playerState, session);

        // Then
        assertEquals(1, result.getCurrentSceneId());
        assertEquals("Неверный ID действия.", result.getActionMessage());
    }

    @Test
    void shouldHandleGameActionWithNullId() {
        // Given
        PlayerState playerState = new PlayerState();
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        GameSessionState result = gameService.handleGameAction(null, 1, playerState, session);

        // Then
        assertEquals(1, result.getCurrentSceneId());
        assertNull(result.getActionMessage());
    }

    @Test
    void shouldHandleGameActionWithEmptyId() {
        // Given
        PlayerState playerState = new PlayerState();
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        GameSessionState result = gameService.handleGameAction("", 1, playerState, session);

        // Then
        assertEquals(1, result.getCurrentSceneId());
        assertNull(result.getActionMessage());
    }

    @Test
    void shouldSearchForMushrooms() {
        // Given
        PlayerState playerState = new PlayerState();

        // When
        String result = gameService.searchForMushrooms(playerState);

        // Then
        assertEquals("Вы нашли грибы!", result);
    }

    @Test
    void shouldUpdateSession() {
        // Given
        PlayerState playerState = new PlayerState();
        GameSessionState gameState = new GameSessionState(playerState, 5, "Тестовое сообщение");
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        gameService.updateSession(session, gameState);

        // Then
        assertEquals(5, session.getAttribute("currentSceneId"));
        assertEquals("Тестовое сообщение", session.getAttribute("actionMessage"));
    }

    @Test
    void shouldUpdateSessionWithNullMessage() {
        // Given
        PlayerState playerState = new PlayerState();
        GameSessionState gameState = new GameSessionState(playerState, 5, null);
        SimpleHttpSession session = new SimpleHttpSession();

        // When
        gameService.updateSession(session, gameState);

        // Then
        assertEquals(5, session.getAttribute("currentSceneId"));
        assertNull(session.getAttribute("actionMessage"));
    }

    @Test
    void shouldGetFilteredScene() {
        // Given
        PlayerState playerState = new PlayerState();
        int sceneId = 1;

        // When
        Question result = gameService.getFilteredScene(sceneId, playerState);

        // Then
        assertNotNull(result);
        assertEquals(sceneId, result.getId());
    }

    @Test
    void shouldGetPrologueWhenSceneNotFound() {
        // Given
        PlayerState playerState = new PlayerState();
        int nonExistentSceneId = 999;

        // When
        Question result = gameService.getFilteredScene(nonExistentSceneId, playerState);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getId());
    }

    // Минимальная реализация HttpSession для Jakarta Servlet API
    private static class SimpleHttpSession implements HttpSession {
        private Map<String, Object> attributes = new HashMap<>();
        private String id = "test-session-" + UUID.randomUUID();
        private long creationTime = System.currentTimeMillis();
        private boolean invalidated = false;

        @Override
        public Object getAttribute(String name) {
            checkValid();
            return attributes.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            checkValid();
            attributes.put(name, value);
        }

        @Override
        public void removeAttribute(String name) {
            checkValid();
            attributes.remove(name);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            checkValid();
            return Collections.enumeration(attributes.keySet());
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getCreationTime() {
            return creationTime;
        }

        @Override
        public long getLastAccessedTime() {
            return System.currentTimeMillis();
        }

        @Override
        public void setMaxInactiveInterval(int interval) {
            // Игнорируем для тестов
        }

        @Override
        public int getMaxInactiveInterval() {
            return 1800;
        }

        @Override
        public jakarta.servlet.ServletContext getServletContext() {
            return null;
        }

        @Override
        public void invalidate() {
            invalidated = true;
            attributes.clear();
        }

        @Override
        public boolean isNew() {
            return attributes.isEmpty();
        }

        private void checkValid() {
            if (invalidated) {
                throw new IllegalStateException("Session has been invalidated");
            }
        }
    }
}