package io.github.anseroville.model.systems;

import io.github.anseroville.model.WorldState;
import io.github.anseroville.model.levels.LevelManager;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

public class CropGrowthSystem {
    private final WorldState worldState;
    private final LevelManager levelManager;

    public CropGrowthSystem(WorldState worldState, LevelManager levelManager) {
        this.worldState = worldState;
        this.levelManager = levelManager;
    }

    public void update(float delta) {
        for (InteractableTile tile : worldState.getTiles()) {
            if(tile instanceof GroundTile)
                ((GroundTile) tile).update(delta, levelManager.getActiveLevelGrowingModifier(),
                        levelManager.getActiveLevelWateringModifier());
        }
    }
}