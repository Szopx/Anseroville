package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

import static io.github.anseroville.model.Tiles.GrowingState.ZERO;

public class GrowingGroundTile extends GroundTile {
    private GrowingState growingState = ZERO;
    private float timeFromLastGrowth = 0f;

    public GrowingGroundTile(EmptyGroundTile emptyGroundTile) {
        super(emptyGroundTile.getGridPosition());
        this.setWatered(emptyGroundTile.isWatered());
    }

    public GrowingGroundTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    public void update(float delta) {
        if (canBeCollected()) {
            return;
        }

        timeFromLastGrowth += delta;

        if (timeFromLastGrowth >= growingState.getTimeToNextState()) {
            grow();
            timeFromLastGrowth = 0f;
        }
    }

    private void grow() {
        growingState = growingState.next();
    }

    public boolean canBeCollected() {
        return growingState.canBeCollected();
    }

    public GrowingState getGrowingState() {
        return growingState;
    }
}