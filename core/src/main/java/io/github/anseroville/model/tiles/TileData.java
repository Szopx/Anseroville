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
        addActivityTiles(tiles);
        addWaterTiles(tiles);

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

    private static void addActivityTiles(Map<GridPosition, InteractableTile> tiles) {
        GridPosition shopPosition = new GridPosition(4, 7);
        tiles.put(shopPosition, new ActivityTile(shopPosition, ActivityTileType.SHOP));

        GridPosition gamblingPosition = new GridPosition(9, 7);
        tiles.put(gamblingPosition, new ActivityTile(gamblingPosition, ActivityTileType.GAMBLING));

        GridPosition mainQuestPosition = new GridPosition(11, 7);
        tiles.put(mainQuestPosition, new ActivityTile(mainQuestPosition, ActivityTileType.MAIN_QUEST));

        GridPosition sideQuestPosition = new GridPosition(15, 7);
        tiles.put(sideQuestPosition, new ActivityTile(sideQuestPosition, ActivityTileType.QUEST));
    }

    private static void addWaterTiles(Map<GridPosition, InteractableTile> tiles) {
        int[][] waterSources = {
                //todo wyznaczyc reszte jeziorka
                {0,17}
        };

        for (int[] water : waterSources) {
            int row = water[0];
            int column = water[1];
            GridPosition position = new GridPosition(column, row);
            tiles.put(position, new ActivityTile(position, ActivityTileType.WATER));
        }
    }
}
