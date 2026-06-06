package io.github.anseroville.model.systems;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

public class CollectingManager {
    private final Inventory inventory;

    public CollectingManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean collect(InteractableTile selectedTile) {
        if (!(selectedTile instanceof GroundTile groundTile)) {
            return false;
        }

        if (!groundTile.canBeCollected()) {
            return false;
        }

        ItemType harvestItem = groundTile.getHarvestItem();

        if (harvestItem == null) {
            return false;
        }

        if (!inventory.add(harvestItem, 1)) {
            return false;
        }

        groundTile.clearCrop();

        return true;
    }
}