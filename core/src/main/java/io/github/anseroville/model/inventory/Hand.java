package io.github.anseroville.model.inventory;

import io.github.anseroville.enums.ItemType;

public class Hand {
    private ItemType type;
    private final Inventory inventory;

    public Hand(Inventory inventory) {
        this.inventory = inventory;
    }

    public void set(ItemType type) {
        this.type = type;
    }

    public void toggleHand(ItemType clickedType) {
        if (clickedType == null) {
            this.clear();
            return;
        }

        if (!this.isEmpty()) {
            ItemType typeInHand = this.getType();
            this.clear();

            if (typeInHand == clickedType) {
                return;
            }
        }

        if (inventory.has(clickedType, 1)) {
            this.set(clickedType);
        }
    }

    public void clear() {
        this.type = null;
    }

    public ItemType getType() { return type; }
    public int getAmount() { return inventory.getAmount(type); }
    public boolean isEmpty() { return type == null; }
}
