package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeActionTest {

    private PlayerState playerState;

    @BeforeEach
    void setUp() {
        playerState = new PlayerState();
    }

    @Test
    void shouldExecuteAllActionsInComposite() {
        // Given
        CompositeAction composite = new CompositeAction(1, "Взять вещи", 5)
                .addAction(new SetFlagAction(0, "", "hasPitchfork", 0))
                .addAction(new AddItemAction(0, "", "ключ", 0));

        // When
        GameResult result = composite.execute(playerState);

        // Then
        assertTrue(playerState.hasFlag("hasPitchfork"));
        assertTrue(playerState.hasItem("ключ"));
        assertEquals(5, result.getNextSceneId());
    }

    @Test
    void shouldHandleEmptyComposite() {
        // Given
        CompositeAction composite = new CompositeAction(1, "Пустое действие", 3);

        // When
        GameResult result = composite.execute(playerState);

        // Then
        assertEquals(3, result.getNextSceneId());
        // Никаких ошибок не должно быть
    }
}
