package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

public class EmptyGroundTile extends GroundTile {
    public EmptyGroundTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    public EmptyGroundTile(GrowingGroundTile growingGroundTile) {
        super(growingGroundTile.getGridPosition());

        // Po zebraniu albo zniszczeniu rośliny pole nie powinno pamiętać,
        // że poprzednia roślina była podlana.
        this.setWatered(false);
    }
}