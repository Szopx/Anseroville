package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;

public class QuestRequirement {

    private final ItemType itemType;
    private final int amount;

    public QuestRequirement(ItemType itemType, int amount) {
        if (itemType == null) {
            throw new IllegalArgumentException("Item type cannot be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Number of quest requirement cannot be less than zero");
        }

        this.itemType = itemType;
        this.amount = amount;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getAmount() {
        return amount;
    }
}
