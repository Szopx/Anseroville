package io.github.anseroville.model;

import io.github.anseroville.model.Tiles.EmptyGroundTile;
import io.github.anseroville.model.Tiles.GrowingGroundTile;

public class Collector {
    private final GameState gameState;

    public Collector(GameState gameState) {
        this.gameState = gameState;
    }

    public void collect(GrowingGroundTile growingGroundTile) {
        gameState.ModifyTile(growingGroundTile.getGridPosition(), new EmptyGroundTile(growingGroundTile));
    }
}
