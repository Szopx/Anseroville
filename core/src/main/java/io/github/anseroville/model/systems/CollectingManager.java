package io.github.anseroville.model.systems;

import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.tiles.*;
import io.github.anseroville.enums.ItemType;

public class CollectingManager {
    private final TileManager tileManager;
    private final Inventory inventory;

    public CollectingManager(TileManager tileManager, Inventory inventory) {
        this.tileManager = tileManager;
        this.inventory = inventory;
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
            addedToInventory = inventory.add(ItemType.CARROT, 1);
        } else if (tile instanceof GrowingCornTile) {
            addedToInventory = inventory.add(ItemType.CORN, 1);
        } else if(tile instanceof GrowingWheatTile) {
            addedToInventory = inventory.add(ItemType.WHEAT, 1);
        } else if(tile instanceof GrowingPotatoTile) {
            addedToInventory = inventory.add(ItemType.POTATO, 1);
        }

        if (!addedToInventory) {
            return false;
        }

        tileManager.modifyTile(growingGroundTile.getGridPosition(), new EmptyGroundTile(growingGroundTile));

        return true;
    }
}