package io.github.anseroville.model.quest;

import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.inventory.ItemType;

import java.util.EnumMap;
import java.util.Map;

public class Quest {
    private final Map<ItemType, Integer> requiredItems;
    private final int rewardMoney;
    private boolean completed;

    public Quest(Map<ItemType, Integer> requiredItems, int rewardMoney) {
        this.requiredItems = new EnumMap<>(ItemType.class);
        this.requiredItems.putAll(requiredItems);
        this.rewardMoney = rewardMoney;
        this.completed = false;
    }

    public boolean canComplete(Inventory inventory) {
        if (completed) {
            return false;
        }

        for (Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()) {
            ItemType itemType = requirement.getKey();
            int requiredAmount = requirement.getValue();

            if (!inventory.has(itemType, requiredAmount)) {
                return false;
            }
        }

        return true;
    }

    public boolean complete(Inventory inventory) {
        if (!canComplete(inventory)) {
            return false;
        }

        for (Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()) {
            ItemType itemType = requirement.getKey();
            int requiredAmount = requirement.getValue();

            inventory.remove(itemType, requiredAmount);
        }

        completed = true;
        return true;
    }

    public int getRewardMoney() {
        return rewardMoney;
    }

    public Map<ItemType, Integer> getRequiredItems(){
        return requiredItems;
    }
}