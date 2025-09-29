package com.khakimov.quest.model;

import java.util.*;

public class PlayerState {
    private String name;
    private Set<String> flags = new HashSet<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private int gamesPlayed = 1;

    // Конструктор по умолчанию
    public PlayerState() {
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

    public void setFlags(Set<String> flags) {
        this.flags = flags;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    // Кастомные методы для флагов
    public void setFlag(String flag) {
        flags.add(flag);
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public void removeFlag(String flag) {
        flags.remove(flag);
    }

    // Кастомные методы для инвентаря
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

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    // equals, hashCode, toString (опционально)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerState that = (PlayerState) o;
        return gamesPlayed == that.gamesPlayed &&
                Objects.equals(name, that.name) &&
                Objects.equals(flags, that.flags) &&
                Objects.equals(inventory, that.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flags, inventory, gamesPlayed);
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "name='" + name + '\'' +
                ", flags=" + flags +
                ", inventory=" + inventory +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
