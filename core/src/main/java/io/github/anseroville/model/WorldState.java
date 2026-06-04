package io.github.anseroville.model;

import io.github.anseroville.model.tiles.InteractableTile;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class WorldState {
    private final Map<GridPosition, InteractableTile> tiles;

    public WorldState(Map<GridPosition, InteractableTile> tiles) {
        this.tiles = tiles;
    }

    public InteractableTile getTile(GridPosition position) {
        return tiles.get(position);
    }

    public Collection<InteractableTile> getTiles() {
        return tiles.values();
    }

    public Map<GridPosition, InteractableTile> getTilesView() {
        return Collections.unmodifiableMap(tiles);
    }
}