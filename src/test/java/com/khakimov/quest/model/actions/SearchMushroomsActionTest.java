package com.khakimov.quest.model.actions;

import com.khakimov.quest.model.PlayerState;
import com.khakimov.quest.model.results.GameResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SearchMushroomsActionTest {

    @Test
    void shouldReturnSpecialMessageForMushrooms() {
        // Given
        PlayerState playerState = new PlayerState();
        SearchMushroomsAction action = new SearchMushroomsAction(1, "Искать грибы", 2);

        // When
        GameResult result = action.execute(playerState);

        // Then
        assertEquals(-1, result.getNextSceneId());
        assertEquals("SEARCH_MUSHROOMS", result.getMessage());
    }
}
