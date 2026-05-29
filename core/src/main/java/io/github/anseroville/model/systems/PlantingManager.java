package io.github.anseroville.model.systems;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.tiles.EmptyGroundTile;
import io.github.anseroville.model.tiles.GrowingCarrotTile;
import io.github.anseroville.model.tiles.GrowingCornTile;
import io.github.anseroville.model.tiles.GrowingGroundTile;
import io.github.anseroville.model.tiles.GrowingPotatoTile;
import io.github.anseroville.model.tiles.GrowingWheatTile;
import io.github.anseroville.model.tiles.InteractableTile;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;

public class PlantingManager {
    private static final float INITIAL_GROWTH_DELTA = 0.1f;

    private final TileManager tileManager;
    private final Hand hand;
    private final Inventory inventory;

    public PlantingManager(TileManager tileManager, Hand hand, Inventory inventory) {
        this.tileManager = tileManager;
        this.hand = hand;
        this.inventory = inventory;
    }

    public boolean plant(InteractableTile selectedTile) {
        if (!(selectedTile instanceof EmptyGroundTile)) {
            System.out.println("Nie udalo sie posadzic.");
            return false;
        }

        EmptyGroundTile emptyGroundTile = (EmptyGroundTile) selectedTile;
        ItemType seedType = hand.getType();
        GrowingGroundTile growingTile = createGrowingTile(seedType, emptyGroundTile);

        if (growingTile == null) {
            System.out.println("Nie udalo sie posadzic.");
            return false;
        }

        if (!inventory.remove(seedType, 1)) {
            hand.clear();
            System.out.println("Nie udalo sie posadzic.");
            return false;
        }

        tileManager.replaceEmptyTile(emptyGroundTile, growingTile);

        // growingTile.update(INITIAL_GROWTH_DELTA);

        if (!inventory.has(seedType, 1)) {
            hand.clear();
        }

        return true;
    }

    public boolean water(InteractableTile selectedTile) {
        if (!(selectedTile instanceof GrowingGroundTile)) {
            System.out.println("Nie ma tutaj rosliny do podlania.");
            return false;
        }

        GrowingGroundTile growingTile = (GrowingGroundTile) selectedTile;

        if (growingTile.canBeCollected()) {
            System.out.println("Ta roslina jest juz gotowa do zebrania.");
            return false;
        }

        if (growingTile.isWatered()) {
            System.out.println("Ta roslina jest juz podlana.");
            return false;
        }

        growingTile.setWatered(true);
        System.out.println("Podlano rosline.");
        return true;
    }

    private GrowingGroundTile createGrowingTile(ItemType seedType, EmptyGroundTile emptyGroundTile) {
        if (seedType == null || !inventory.has(seedType, 1)) {
            return null;
        }

        switch (seedType) {
            case CARROT_SEED:
                System.out.println("Posadz marchewki.");
                return new GrowingCarrotTile(emptyGroundTile);
            case POTATO_SEED:
                System.out.println("Posadz ziemniaki.");
                return new GrowingPotatoTile(emptyGroundTile);
            case CORN_SEED:
                System.out.println("Posadz kukurydze.");
                return new GrowingCornTile(emptyGroundTile);
            case WHEAT_SEED:
                System.out.println("Posadz pszenice.");
                return new GrowingWheatTile(emptyGroundTile);
            default:
                return null;
        }
    }
}