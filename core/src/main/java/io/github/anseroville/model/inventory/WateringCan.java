package io.github.anseroville.model.inventory;

public class WateringCan {
    private final int maxWater;
    private int currentWater;

    public WateringCan(int maxWater) {
        this.maxWater = maxWater;
        this.currentWater = maxWater;
    }

    public int getCurrentWater() {
        return currentWater;
    }

    public int getMaxWater() {
        return maxWater;
    }

    public boolean useWater(int amount) {
        if (currentWater >= amount) {
            currentWater -= amount;
            return true;
        }
        return false;
    }

    public void refill() {
        this.currentWater = maxWater;
    }
}
