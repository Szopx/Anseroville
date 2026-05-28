package io.github.anseroville.model.systems;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;

public class HandManager {
    private final Hand hand;
    private final Inventory inventory;

    public HandManager(Hand hand, Inventory inventory) {
        this.hand = hand;
        this.inventory = inventory;
    }

    public void toggleHand(ItemType clickedType) {
        if (clickedType == null) {
            hand.clear();
            return;
        }

        if (!hand.isEmpty()) {
            ItemType typeInHand = hand.getType();
            hand.clear();

            if (typeInHand == clickedType) {
                return;
            }
        }

        if (inventory.has(clickedType, 1)) {
            hand.set(clickedType);
        }
    }
}
