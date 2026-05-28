package io.github.anseroville.model.data;

import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.Tiles.EmptyGroundTile;
import io.github.anseroville.model.Tiles.InteractableTile;
import io.github.anseroville.model.Tiles.ActivityTile;
import io.github.anseroville.model.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public final class GameData {

    public static final int STARTING_MONEY = 20;

    private GameData() {
    }

    public static Map<GridPosition, InteractableTile> createInteractableTiles() {
        Map<GridPosition, InteractableTile> tiles = new HashMap<>();

        addFarmFields(tiles);
        addShopTiles(tiles);

        return tiles;
    }

    public static void addStartingInventory(Inventory inventory) {
        inventory.add(ItemType.CARROT_SEED, 10);
        inventory.add(ItemType.WHEAT_SEED, 6);
        inventory.add(ItemType.POTATO_SEED, 4);
        inventory.add(ItemType.CORN_SEED, 3);
        inventory.add(ItemType.TORCH, 2);
    }

    private static void addFarmFields(Map<GridPosition, InteractableTile> tiles) {
        int[][] fields = {
                {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7},
                {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7},
                {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7},
                {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}
        };

        for (int[] field : fields) {
            int row = field[0];
            int column = field[1];
            GridPosition position = new GridPosition(column, row);
            tiles.put(position, new EmptyGroundTile(position));
        }
    }

    private static void addShopTiles(Map<GridPosition, InteractableTile> tiles) {
        int[][] shops = {
                {1, 8}, {1, 9}
        };

        for (int[] shop : shops) {
            int row = shop[0];
            int column = shop[1];
            GridPosition position = new GridPosition(column, row);
            tiles.put(position, new ActivityTile(position, ActivityTileType.SHOP));
        }
    }
}
