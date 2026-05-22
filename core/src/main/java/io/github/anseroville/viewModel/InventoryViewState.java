package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Map;

public class InventoryViewState {
    private final Map<ItemType, Integer> items;
    private final ItemType heldItemType;
    private final int heldItemAmount;

    public InventoryViewState(Map<ItemType, Integer> items, ItemType heldItemType, int heldItemAmount) {
        this.items = Map.copyOf(items);
        this.heldItemType=heldItemType;
        this.heldItemAmount=heldItemAmount;
    }

    //czy tu powinno sumowac sie z tym co jest w hand? tak todo
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
}
