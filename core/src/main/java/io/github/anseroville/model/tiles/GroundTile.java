package io.github.anseroville.model.tiles;

import io.github.anseroville.enums.CropType;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;

public class GroundTile extends InteractableTile {
    private static final float WATERED_DURATION = 40.0f;

    private Crop crop;
    private float wateredTimer = 0.0f;

    public GroundTile(GridPosition gridPosition) {
        super(gridPosition);
        this.crop = null;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    public boolean hasCrop() {
        return crop != null;
    }

    public boolean plant(CropType cropType) {
        if (cropType == null || crop != null) {
            return false;
        }

        crop = new Crop(cropType);

        return true;
    }

    public boolean water() {
        if (wateredTimer == WATERED_DURATION) {
            return false;
        }

        wateredTimer = WATERED_DURATION;
        return true;
    }

    public void update(float delta) {
        if (wateredTimer > 0) {
            wateredTimer -= delta;
            if (wateredTimer < 0) {
                wateredTimer = 0;
            }
        }

        if (crop == null) {
            return;
        }

        crop.update(delta, isWatered());
    }

    public boolean canBeCollected() {
        return crop != null && crop.canBeCollected();
    }

    public ItemType getHarvestItem() {
        if (crop == null) {
            return null;
        }

        if (!crop.canBeCollected()) {
            return null;
        }

        return crop.getHarvestItem();
    }

    public void clearCrop() {
        crop = null;
    }

    public void clearWatered() {
        wateredTimer = 0.0f;
    }

    public Crop getCrop() {
        return crop;
    }

    public boolean isWatered() {
        return wateredTimer > 0;
    }
}