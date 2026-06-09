package io.github.anseroville.model.tiles;

import io.github.anseroville.enums.CropType;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;

import java.util.concurrent.ThreadLocalRandom;

public class Crop {
    private final CropType type;
    private GrowingState growingState = GrowingState.SMALL;
    private float timeFromLastGrowth = 0f;
    private final int growthModifier;

    public Crop(CropType type) {
        this.type = type;
        this.growthModifier = ThreadLocalRandom.current().nextInt(-2, 3);
    }

    public void update(float delta, boolean watered) {
        if (!watered || canBeCollected()) {
            return;
        }

        timeFromLastGrowth += delta;

        float duration = getCurrentGrowthDuration();

        if (timeFromLastGrowth >= duration) {
            growingState = growingState.next();
            timeFromLastGrowth = 0f;
        }
    }

    public boolean canBeCollected() {
        return growingState.canBeCollected();
    }

    public ItemType getHarvestItem() {
        return type.getHarvestItem();
    }

    public GrowingState getGrowingState() {
        return growingState;
    }

    private float getCurrentGrowthDuration() {
        return type.getGrowthDuration(growingState.ordinal()) + growthModifier;
    }
}