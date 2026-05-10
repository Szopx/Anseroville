package io.github.anseroville.model.inventory;

public class Hand {
    private ItemType type;
    private int amount;

    public int getAmountOf(ItemType checkedType) {
        return (this.type == checkedType) ? amount : 0;
    }

    public void set(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public void clear() {
        this.type = null;
        this.amount = 0;
    }

    public ItemType getType() { return type; }
    public int getAmount() { return amount; }
    public boolean isEmpty() { return type == null; }
}
