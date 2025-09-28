package com.khakimov.quest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@NoArgsConstructor
public class PlayerState {
    private String name;
    private Set<String> flags = new HashSet<>();
    private Map<String, Integer> inventory = new HashMap<>();
    private int gamesPlayed = 1;

    public void setFlag(String flag) {
        flags.add(flag);
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public void removeFlag(String flag) {
        flags.remove(flag);
    }

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
}
