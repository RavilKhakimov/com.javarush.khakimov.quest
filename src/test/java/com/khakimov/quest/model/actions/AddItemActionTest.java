package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddItemActionTest {

    private PlayerState playerState;

    @BeforeEach
    void setUp() {
        playerState = new PlayerState();
    }

    @Test
    void shouldAddItemToInventory() {
        // Given
        AddItemAction action = new AddItemAction(1, "Взять ключ", "ключ", 2);

        // When
        GameResult result = action.execute(playerState);

        // Then
        assertTrue(playerState.hasItem("ключ"));
        assertEquals(1, playerState.getItemCount("ключ"));
        assertEquals(2, result.getNextSceneId());
        assertEquals("Вы получили: ключ", result.getMessage());
    }

    @Test
    void shouldIncrementExistingItemCount() {
        // Given
        playerState.addItem("ключ");
        AddItemAction action = new AddItemAction(1, "Взять ключ", "ключ", 2);

        // When
        action.execute(playerState);

        // Then
        assertEquals(2, playerState.getItemCount("ключ"));
    }
}
