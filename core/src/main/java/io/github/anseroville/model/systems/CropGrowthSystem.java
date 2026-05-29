package io.github.anseroville.model.systems;

import io.github.anseroville.model.tiles.GrowingGroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

public class CropGrowthSystem {
    private final TileManager tileManager;

    public CropGrowthSystem(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void update(float delta) {
        for (InteractableTile tile : tileManager.getInteractableTiles().values()) {
            if (tile instanceof GrowingGroundTile) {
                GrowingGroundTile growingTile = (GrowingGroundTile) tile;
                growingTile.update(delta);
            }
        }
    }
}
