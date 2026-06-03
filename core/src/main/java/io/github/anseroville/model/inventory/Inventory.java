package io.github.anseroville.model.inventory;

import io.github.anseroville.enums.ItemType;

import java.util.EnumMap;
import java.util.Map;

public class Inventory {
    private static final int MAX_AMOUNT_PER_ITEM = 64;

    private final Map<ItemType, Integer> items = new EnumMap<>(ItemType.class);

    public boolean add(ItemType itemType, int amount) {
        if (itemType == null || amount <= 0) {
            return false;
        }

        int currentAmount = getAmount(itemType);

        if (currentAmount + amount > MAX_AMOUNT_PER_ITEM) {
            return false;
        }

        items.put(itemType, currentAmount + amount);
        return true;
    }

    public boolean remove(ItemType itemType, int amount) {
        if (itemType == null || amount <= 0) {
            return false;
        }

        int currentAmount = getAmount(itemType);

        if (currentAmount < amount) {
            return false;
        }

        items.put(itemType, currentAmount - amount);
        return true;
    }

    public boolean has(ItemType itemType, int amount) {
        return getAmount(itemType) >= amount;
    }

    public int getAmount(ItemType itemType) {
        if (itemType == null) {
            return 0;
        }

        return items.getOrDefault(itemType, 0);
    }

    public void clear() {
        items.clear();
    }
}