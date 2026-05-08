package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

public class GrassGroundTile extends GroundTile {

    public GrassGroundTile(EmptyGroundTile emptyGroundTile) {
        super(emptyGroundTile.getGridPosition());
        this.setWatered(emptyGroundTile.isWatered());
    }

    public GrassGroundTile(GridPosition gridPosition) {
        super(gridPosition);
    }
}
