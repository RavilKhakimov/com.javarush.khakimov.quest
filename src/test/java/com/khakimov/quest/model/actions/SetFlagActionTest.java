package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SetFlagActionTest {

    private PlayerState playerState;

    @BeforeEach
    void setUp() {
        playerState = new PlayerState();
    }

    @Test
    void shouldSetFlagForPlayer() {
        // Given
        SetFlagAction action = new SetFlagAction(1, "Взять вилы", "hasPitchfork", 2);

        // When
        GameResult result = action.execute(playerState);

        // Then
        assertTrue(playerState.hasFlag("hasPitchfork"));
        assertEquals(2, result.getNextSceneId());
    }

    @Test
    void shouldSetMultipleFlags() {
        // Given
        SetFlagAction action1 = new SetFlagAction(1, "Флаг 1", "flag1", 2);
        SetFlagAction action2 = new SetFlagAction(2, "Флаг 2", "flag2", 3);

        // When
        action1.execute(playerState);
        action2.execute(playerState);

        // Then
        assertTrue(playerState.hasFlag("flag1"));
        assertTrue(playerState.hasFlag("flag2"));
    }
}
