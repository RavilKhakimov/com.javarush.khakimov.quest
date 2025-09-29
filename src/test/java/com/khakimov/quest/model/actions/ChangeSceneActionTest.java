package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChangeSceneActionTest {

    @Test
    void shouldChangeSceneWithoutModifyingPlayer() {
        // Given
        PlayerState playerState = new PlayerState();
        playerState.setName("Игрок");
        ChangeSceneAction action = new ChangeSceneAction(1, "Перейти в лес", 3);

        // When
        GameResult result = action.execute(playerState);

        // Then
        assertEquals(3, result.getNextSceneId());
        assertEquals("", result.getMessage());
        assertEquals("Игрок", playerState.getName()); // Состояние не изменилось
    }
}
