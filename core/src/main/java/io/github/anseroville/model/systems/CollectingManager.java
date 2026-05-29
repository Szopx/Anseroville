package io.github.anseroville.model.systems;

import io.github.anseroville.model.GameState;
import io.github.anseroville.model.tiles.*;
import io.github.anseroville.enums.ItemType;

public class CollectingManager {
    private final GameState gameState;

    public CollectingManager(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean collect(InteractableTile tile) {
        if (!(tile instanceof GrowingGroundTile growingGroundTile)) {
            return false;
        }

        if (!growingGroundTile.canBeCollected()) {
            return false;
        }

        boolean addedToInventory = false;

        if(tile instanceof GrowingCarrotTile) {
            addedToInventory = gameState.getInventory().add(ItemType.CARROT, 1);
        } else if (tile instanceof GrowingCornTile) {
            addedToInventory = gameState.getInventory().add(ItemType.CORN, 1);
        } else if(tile instanceof GrowingWheatTile) {
            addedToInventory = gameState.getInventory().add(ItemType.WHEAT, 1);
        } else if(tile instanceof GrowingPotatoTile) {
            addedToInventory = gameState.getInventory().add(ItemType.POTATO, 1);
        }

        if (!addedToInventory) {
            return false;
        }

        gameState.modifyTile(growingGroundTile.getGridPosition(), new EmptyGroundTile(growingGroundTile));

        return true;
    }
}