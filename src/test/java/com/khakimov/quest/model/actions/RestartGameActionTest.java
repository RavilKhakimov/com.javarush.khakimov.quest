// src/test/java/com/khakimov/quest/model/actions/RestartGameActionTest.java
package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestartGameActionTest {

    @Test
    void shouldResetPlayerStateButKeepGamesCounter() {
        // Given
        PlayerState playerState = new PlayerState();
        playerState.setName("Иван");
        playerState.setFlag("hasPitchfork");
        playerState.addItem("вилы");
        playerState.addItem("ключ");

        // Начальный счетчик = 1, увеличится до 2 при выполнении
        RestartGameAction action = new RestartGameAction(1, "Перезапуск", 0);

        // When
        GameResult result = action.execute(playerState);

        // Then
        // Проверяем сброс состояния
        assertNull(playerState.getName());
        assertFalse(playerState.hasFlag("hasPitchfork"));
        assertFalse(playerState.hasItem("вилы"));
        assertFalse(playerState.hasItem("ключ"));

        // Проверяем что счетчик сохранился и увеличился
        assertEquals(2, playerState.getGamesPlayed());

        // Проверяем результат действия
        assertEquals(0, result.getNextSceneId());
        assertEquals("Игра перезапущена! Это ваша 2 игра.", result.getMessage());
    }

    @Test
    void shouldIncrementGamesPlayedCounter() {
        // Given
        PlayerState playerState = new PlayerState();
        // Начальное значение gamesPlayed = 1
        RestartGameAction action = new RestartGameAction(1, "Перезапуск", 0);

        // When
        action.execute(playerState);

        // Then - счетчик должен увеличиться с 1 до 2
        assertEquals(2, playerState.getGamesPlayed());
    }
}