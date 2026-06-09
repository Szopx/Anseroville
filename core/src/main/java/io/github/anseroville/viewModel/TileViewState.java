package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;

public class TileViewState {
    private final int x;
    private final int y;
    private final boolean selected;
    private final GrowingState growingState;
    private final ItemType grows;
    private final boolean watered;
    private final ActivityTileType activityTileType;

    private final int SCALE = 75;

    public TileViewState(int x, int y, boolean selected, GrowingState growingstate,
                         ItemType grows, boolean watered, ActivityTileType activityTileType) {
        this.x = x * SCALE;
        this.y = y * SCALE;
        this.selected = selected;
        this.growingState = growingstate;
        this.grows = grows;
        this.watered = watered;
        this.activityTileType = activityTileType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ItemType whatGrows(){
        return grows;
    }

    public boolean isSelected() {
        return selected;
    }

    public GrowingState getGrowingState() { return growingState; }

    public boolean isWatered() { return watered;}

    public ActivityTileType getActivityTileType() { return activityTileType; }
}