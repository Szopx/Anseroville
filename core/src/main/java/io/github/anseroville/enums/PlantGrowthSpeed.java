package io.github.anseroville.enums;

public enum PlantGrowthSpeed {
    SLOW("SLOW", 0.5f),
    NORMAL("NORMAL", 1f),
    FAST("FAST", 2f);

    private final String label;
    private final float growthMultiplier;

    PlantGrowthSpeed(String label, float growthMultiplier) {
        this.label = label;
        this.growthMultiplier = growthMultiplier;
    }

    public String getLabel() {
        return label;
    }

    public float getGrowthMultiplier() {
        return growthMultiplier;
    }

    public PlantGrowthSpeed next() {
        return switch (this) {
            case SLOW -> NORMAL;
            case NORMAL -> FAST;
            case FAST -> SLOW;
        };
    }
}