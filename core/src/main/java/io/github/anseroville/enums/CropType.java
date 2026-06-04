package io.github.anseroville.enums;

import io.github.anseroville.enums.ItemType;

public enum CropType {
    CARROT(ItemType.CARROT_SEED, ItemType.CARROT, new float[] {5f, 4f}),
    POTATO(ItemType.POTATO_SEED, ItemType.POTATO, new float[] {6f, 5f}),
    CORN(ItemType.CORN_SEED, ItemType.CORN, new float[] {7f, 6f}),
    WHEAT(ItemType.WHEAT_SEED, ItemType.WHEAT, new float[] {4f, 3f});

    private final ItemType seedItem;
    private final ItemType harvestItem;
    private final float[] growthDurations;

    CropType(ItemType seedItem, ItemType harvestItem, float[] growthDurations) {
        this.seedItem = seedItem;
        this.harvestItem = harvestItem;
        this.growthDurations = growthDurations;
    }

    public ItemType getSeedItem() {
        return seedItem;
    }

    public ItemType getHarvestItem() {
        return harvestItem;
    }

    public float getGrowthDuration(int stageIndex) {
        if (stageIndex < 0 || stageIndex >= growthDurations.length) {
            return 0f;
        }

        return growthDurations[stageIndex];
    }

    public static CropType fromSeed(ItemType seedItem) {
        for (CropType cropType : values()) {
            if (cropType.seedItem == seedItem) {
                return cropType;
            }
        }

        return null;
    }
}