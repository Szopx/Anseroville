package io.github.anseroville.model.Tiles;

import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.model.GridPosition;

import static io.github.anseroville.enums.GrowingState.ZERO;

public class GrowingGroundTile extends GroundTile {
    protected GrowingState growingState = ZERO;
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

        if (timeFromLastGrowth >= this.getDuration()) {
            grow();
            timeFromLastGrowth = 0f;
        }
    }

    protected int getDuration(){
        if (growingState.ordinal() == 0){
            return 0;
        }
        return 3;
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