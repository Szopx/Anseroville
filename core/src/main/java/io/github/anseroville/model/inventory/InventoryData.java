package io.github.anseroville.model.inventory;

import io.github.anseroville.enums.ItemType;

public class InventoryData {
    public static final int STARTING_MONEY = 20;

    private InventoryData() {
    }

    public static void addStartingInventory(Inventory inventory) {
        inventory.add(ItemType.CARROT_SEED, 10);
        inventory.add(ItemType.WHEAT_SEED, 6);
        inventory.add(ItemType.POTATO_SEED, 4);
        inventory.add(ItemType.CORN_SEED, 3);
        inventory.add(ItemType.TORCH, 2);
    }
}
