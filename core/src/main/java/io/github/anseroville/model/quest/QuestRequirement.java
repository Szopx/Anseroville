package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;

public class QuestRequirement {

    private final ItemType itemType;
    private final int amount;

    public QuestRequirement(ItemType itemType, int amount) {
        if (itemType == null) {
            throw new IllegalArgumentException("lipa z tym ze item jest null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("liczba <= 0");
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
