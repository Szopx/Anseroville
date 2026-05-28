package io.github.anseroville.model.Tiles;

import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.model.GridPosition;

public class ActivityTile extends InteractableTile{
    private final ActivityTileType activityTileType;

    public ActivityTile(GridPosition gridPosition, ActivityTileType activityTileType) {
        super(gridPosition);
        this.activityTileType = activityTileType;
    }

    public ActivityTileType getActivityTileType()
    {
        return activityTileType;
    }
}
