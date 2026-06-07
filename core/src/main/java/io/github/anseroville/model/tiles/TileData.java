package io.github.anseroville.model.tiles;

import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.model.GridPosition;

import java.util.HashMap;
import java.util.Map;

public class TileData {

    private TileData() {
    }

    public static Map<GridPosition, InteractableTile> createInteractableTiles() {
        Map<GridPosition, InteractableTile> tiles = new HashMap<>();

        addFarmFields(tiles);
        addShopTiles(tiles);

        return tiles;
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
            tiles.put(position, new GroundTile(position));
        }
    }

    private static void addShopTiles(Map<GridPosition, InteractableTile> tiles) {
        int[][] shops = {
                {7,4}
        };

        for (int[] shop : shops) {
            int row = shop[0];
            int column = shop[1];
            GridPosition position = new GridPosition(column, row);
            tiles.put(position, new ActivityTile(position, ActivityTileType.SHOP));
        }
        //todo rzucić gdzie indziej ale jest pozno
        GridPosition position = new GridPosition(9, 7);
        tiles.put(position, new ActivityTile(position, ActivityTileType.GAMBLING));
    }
}
