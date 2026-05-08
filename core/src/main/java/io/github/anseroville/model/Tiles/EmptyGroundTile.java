package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

public class EmptyGroundTile extends GroundTile {

    public EmptyGroundTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    public EmptyGroundTile(GrowingGroundTile growingGroundTile) {
        super(growingGroundTile.getGridPosition());
        this.setWatered(growingGroundTile.isWatered());
    }
}
