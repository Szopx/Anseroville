package io.github.anseroville.model.inventory;

public class Hand {
    private ItemType type;
    private Inventory inventory;

    public Hand(Inventory inventory) {
        this.inventory = inventory;
    }

    public void set(ItemType type) {
        this.type = type;
    }

    public void clear() {
        this.type = null;
    }

    public ItemType getType() { return type; }
    public int getAmount() { return inventory.getAmount(type); }
    public boolean isEmpty() { return type == null; }
}
