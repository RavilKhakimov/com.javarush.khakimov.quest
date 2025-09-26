package com.khakimov.quest.model;

import java.util.*;

public class PlayerState {
    private String name;
    private Set<String> flags; // Флаги
    private Map<String, Integer> inventory; // Инвентарь: предмет -> количество
    private int gamesPlayed; //счетчик

    public PlayerState() {
        this.flags = new HashSet<>();
        this.inventory = new HashMap<>();
        this.gamesPlayed = 1;
    }

    // флаги
    public void setFlag(String flag) {
        flags.add(flag);
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public void removeFlag(String flag) {
        flags.remove(flag);
    }

    // инвентарь
    public void addItem(String item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    public void removeItem(String item) {
        if (inventory.containsKey(item)) {
            int count = inventory.get(item);
            if (count > 1) {
                inventory.put(item, count - 1);
            } else {
                inventory.remove(item);
            }
        }
    }

    public boolean hasItem(String item) {
        return inventory.containsKey(item) && inventory.get(item) > 0;
    }

    public int getItemCount(String item) {
        return inventory.getOrDefault(item, 0);
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getFlags() {
        return flags;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }
}