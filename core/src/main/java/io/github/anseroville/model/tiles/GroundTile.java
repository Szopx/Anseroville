package io.github.anseroville.model.tiles;

import io.github.anseroville.enums.CropType;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;

public class GroundTile extends InteractableTile {
    private Crop crop;
    private boolean watered = false;

    public GroundTile(GridPosition gridPosition) {
        super(gridPosition);
        crop = null;
    }

    public boolean isEmpty() {
        return crop == null;
    }

    public boolean hasCrop() {
        return crop != null;
    }

    public boolean plant(CropType cropType) {
        if (crop != null) {
            return false;
        }

        crop = new Crop(cropType);
        return true;
    }

    public boolean water() {
        if (crop == null || watered) {
            return false;
        }

        watered = true;
        return true;
    }

    public void update(float delta) {
        if (crop == null) {
            return;
        }

        crop.update(delta, watered);
    }

    public boolean canBeCollected() {
        return crop != null && crop.canBeCollected();
    }

    public ItemType getHarvestItem() {
        return crop.getHarvestItem();
    }

    public void clearCrop() {
        crop = null;
        watered = false;
    }

    public Crop getCrop() {
        return crop;
    }

    public boolean isWatered() {
        return watered;
    }
}