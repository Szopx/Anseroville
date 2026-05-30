package io.github.anseroville.model.systems;

import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.tiles.EmptyGroundTile;
import io.github.anseroville.model.tiles.GrowingGroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

import java.util.Map;

public class TileManager {
    private final Map<GridPosition, InteractableTile> interactableTiles;

    public TileManager(Map<GridPosition, InteractableTile> interactableTiles) {
        this.interactableTiles = interactableTiles;
    }

    public Map<GridPosition, InteractableTile> getInteractableTiles() {
        return interactableTiles;
    }

    public InteractableTile getTile(GridPosition position) {
        return interactableTiles.get(position);
    }

    public void modifyTile(GridPosition position, InteractableTile interactableTile) {
        interactableTiles.put(position, interactableTile);
    }

    public void replaceEmptyTile(EmptyGroundTile emptyGroundTile, GrowingGroundTile growingGroundTile) {
        if (emptyGroundTile.isSelected()) {
            growingGroundTile.select();
        }

        modifyTile(emptyGroundTile.getGridPosition(), growingGroundTile);
    }

    public void clearGrowingTile(GridPosition position) {
        InteractableTile oldTile = interactableTiles.get(position);

        if (!(oldTile instanceof GrowingGroundTile)) {
            return;
        }

        GrowingGroundTile growingGroundTile = (GrowingGroundTile) oldTile;
        EmptyGroundTile emptyGroundTile = new EmptyGroundTile(growingGroundTile);

        if (oldTile.isSelected()) {
            emptyGroundTile.select();
        }

        modifyTile(position, emptyGroundTile);
    }
}
