package io.github.anseroville.model;

import io.github.anseroville.model.Tiles.EmptyGroundTile;
import io.github.anseroville.model.Tiles.GrowingGroundTile;
import io.github.anseroville.model.Tiles.InteractableTile;
import io.github.anseroville.model.inventory.ItemType;

public class Collector {
    private final GameState gameState;

    public Collector(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean collect(InteractableTile tile) {
        if (!(tile instanceof GrowingGroundTile growingGroundTile)) {
            return false;
        }

        if (!growingGroundTile.canBeCollected()) {
            return false;
        }

        boolean addedToInventory = gameState.getInventory().add(ItemType.CARROT, 1);

        if (!addedToInventory) {
            return false;
        }

        gameState.modifyTile(growingGroundTile.getGridPosition(), new EmptyGroundTile(growingGroundTile));

        return true;
    }
}