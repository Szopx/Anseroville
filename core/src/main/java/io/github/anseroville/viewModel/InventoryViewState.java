package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Map;

public class InventoryViewState {
    private final Map<ItemType, Integer> items;
    private final ItemType heldItemType;
    private final int heldItemAmount;
    private final int currentWater;
    private final int maxWater;

    public InventoryViewState(Map<ItemType, Integer> items, ItemType heldItemType, int heldItemAmount,
    int currentWater, int maxWater) {
        this.items = Map.copyOf(items);
        this.heldItemType=heldItemType;
        this.heldItemAmount=heldItemAmount;
        this.currentWater = currentWater;
        this.maxWater = maxWater;
    }

    public int getAmount(ItemType itemType) {
        return items.getOrDefault(itemType, 0);
    }

    public ItemType getHeldItemType() {
        return heldItemType;
    }

    public int getHeldItemAmount() {
        return heldItemAmount;
    }

    public boolean hasItemInHand() {
        return heldItemType != null && heldItemAmount > 0;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public int getMaxWater() {
        return maxWater;
    }
}
