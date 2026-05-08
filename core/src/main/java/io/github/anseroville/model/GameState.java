package io.github.anseroville.model;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private static final int TILE_WIDTH = 75; //? zmienić żeby w rendertiles nie było fixed
    private static final int TILE_HEIGHT = 73;//?

    private final Player player;
    private final Map<GridPosition, InteractableTile> interactableTiles;

    public GameState() {
        this.player = new Player(100, 100);
        this.interactableTiles = createInteractableTiles();
    }

    private Map<GridPosition, InteractableTile> createInteractableTiles() {
        int[][] interactablePlaces = {
                {0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 12}, {0, 13}, {0, 14},
                {1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4},
                {2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4},
                {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6},
                {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6},
                {6, 1}, {6, 2}, {6, 4}, {6, 5}, {6, 6}
        }; //wywalić do innego pliku

        Map<GridPosition, InteractableTile> tiles = new HashMap<>();

        for (int[] place : interactablePlaces) {
            int row = place[0];
            int column = place[1];

            GridPosition gridPosition = new GridPosition(column, row);
            tiles.put(gridPosition,new InteractableTile(gridPosition));
        }

        return tiles;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<GridPosition, InteractableTile> getInteractableTiles() {
        return interactableTiles;
    }
}