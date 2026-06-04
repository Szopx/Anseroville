package io.github.anseroville.model.systems;

import io.github.anseroville.enums.CropType;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

public class PlantingManager {
    private final Hand hand;
    private final Inventory inventory;

    public PlantingManager(Hand hand, Inventory inventory) {
        this.hand = hand;
        this.inventory = inventory;
    }

    public boolean plant(InteractableTile selectedTile) {
        if (!(selectedTile instanceof GroundTile groundTile)) {
            return false;
        }

        ItemType seedType = hand.getType();
        CropType cropType = CropType.fromSeed(seedType);

        if (cropType == null) {
            return false;
        }

        if (!groundTile.isEmpty()) {
            return false;
        }

        if (!inventory.remove(seedType, 1)) {
            hand.clear();
            return false;
        }

        boolean planted = groundTile.plant(cropType);

        if (!planted) {
            inventory.add(seedType, 1);
            return false;
        }

        if (!inventory.has(seedType, 1)) {
            hand.clear();
        }

        return true;
    }

    public boolean water(InteractableTile selectedTile) {
        if (!(selectedTile instanceof GroundTile groundTile)) {
            return false;
        }

        return groundTile.water();
    }
}