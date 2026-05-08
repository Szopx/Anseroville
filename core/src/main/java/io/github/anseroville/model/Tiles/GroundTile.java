package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

public class GroundTile extends InteractableTile {

    private boolean isWatered = false;

    public GroundTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }
}
