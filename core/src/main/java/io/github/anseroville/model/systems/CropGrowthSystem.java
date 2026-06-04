package io.github.anseroville.model.systems;

import io.github.anseroville.model.WorldState;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

public class CropGrowthSystem {
    private final WorldState worldState;

    public CropGrowthSystem(WorldState worldState) {
        this.worldState = worldState;
    }

    public void update(float delta) {
        for (InteractableTile tile : worldState.getTiles()) {
            if(tile instanceof GroundTile)
                ((GroundTile) tile).update(delta);
        }
    }
}