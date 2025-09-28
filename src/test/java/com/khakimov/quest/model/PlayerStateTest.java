package com.khakimov.quest.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerStateTest {

    private PlayerState player;

    @BeforeEach
    void setUp() {
        player = new PlayerState();
    }

    @Test
    void shouldCreatePlayerWithDefaultValues() {
        // Then (проверяем значения по умолчанию)
        assertNull(player.getName(), "The name must be null when creating it");
        assertTrue(player.getFlags().isEmpty(), "The flags must be Empty");
        assertTrue(player.getInventory().isEmpty(), "Inventory must be Empty");
        assertEquals(1, player.getGamesPlayed(), "Games counter must begin from 1");
    }

    @Test
    void shouldSetAndGetPlayerName() {

        player.setName("Harry");

        assertEquals("Harry", player.getName(), "PlayerName should set correct");
    }

    @Test
    void shouldSetAndCheckFlags() {

        player.setFlag("trueFlag");

        assertTrue(player.hasFlag("trueFlag"), "Flag should set correct");
        assertFalse(player.hasFlag("noTrueFlag"), "A non-existent flag should return false");
    }

    @Test
    void shouldRemoveFlag() {

        player.setFlag("trueFlag");
        assertTrue(player.hasFlag("trueFlag"));

        player.removeFlag("trueFlag");
        assertFalse(player.hasFlag("trueFlag"), "Flag should remove");

    }

    @Test
    void shouldAddItemToInventory() {

        player.addItem("mushroom");
        player.addItem("mushroom");

        assertTrue(player.hasItem("mushroom"), "Item should Add");
        assertEquals(2, player.getItemCount("mushroom"), "The number should increase");
    }

    @Test
    void shouldRemoveItemFromInventory() {

        player.addItem("mushroom");
        player.addItem("mushroom");

        assertTrue(player.hasItem("mushroom"));
        assertEquals(2, player.getItemCount("mushroom"));

        player.removeItem("mushroom");

        assertTrue(player.hasItem("mushroom"), "The item must remain");
        assertEquals(1, player.getItemCount("mushroom"), "The item must remove");

        player.removeItem("mushroom");

        assertFalse(player.hasItem("mushroom"), "The number should decrease by 1");
        assertEquals(0, player.getItemCount("mushroom"), "The number should decrease by 0");
    }

    @Test
    void shouldHandleRemovingNonexistentItem() {

        player.removeItem("nonExistItem");

        assertTrue(player.getInventory().isEmpty(), "Inventory must be empty");
        assertFalse(player.hasItem("nonExistItem"), "Non existent item must be non exist");
    }

    @Test
    void shouldIncrementGamesPlayed() {

        int initialGames = player.getGamesPlayed();

        player.incrementGamesPlayed();
        player.incrementGamesPlayed();

        assertEquals(initialGames + 2, player.getGamesPlayed(),
                "Games counter must be increment for 2");
    }

    @Test
    void shouldSetGamesPlayed() {

        player.setGamesPlayed(5);

        assertEquals(5, player.getGamesPlayed(),
                "The counter must be set to the value");
    }

    @Test
    void shouldHandleMultipleItems() {

        player.addItem("fork");
        player.addItem("key");
        player.addItem("map");

        assertEquals(3, player.getInventory().size(), "Should be 3 different items");
        assertTrue(player.hasItem("fork"));
        assertTrue(player.hasItem("key"));
        assertTrue(player.hasItem("map"));
        assertFalse(player.hasItem("gold"));
    }

    @Test
    void shouldGetItemCountForNonexistentItem() {

        assertEquals(0, player.getItemCount("nonexistentItem"),
                "Count of nonexistentItem should be 0");
    }
}
