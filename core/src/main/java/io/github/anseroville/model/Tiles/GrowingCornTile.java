package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

import java.util.concurrent.ThreadLocalRandom;

public class GrowingCornTile extends GrowingGroundTile {
    private final int growthModifier = ThreadLocalRandom.current().nextInt(0, 6);

    public GrowingCornTile(EmptyGroundTile emptyGroundTile) {
        super(emptyGroundTile);
    }

    GrowingCornTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    @Override
    protected int getDuration(){
        return switch (growingState.ordinal()) {
            case 0 -> 0;
            case 1 -> 3 + growthModifier;
            case 2 -> 7 + growthModifier;
            case 3 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + growingState.ordinal());
        };
    }
}
