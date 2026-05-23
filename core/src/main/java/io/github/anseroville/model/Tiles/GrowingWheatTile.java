package io.github.anseroville.model.Tiles;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;

import java.util.concurrent.ThreadLocalRandom;

public class GrowingWheatTile extends GrowingGroundTile{
    private final int growthModifier = ThreadLocalRandom.current().nextInt(-1, 2);

    public GrowingWheatTile(EmptyGroundTile emptyGroundTile) {
        super(emptyGroundTile);
    }

    GrowingWheatTile(GridPosition gridPosition) {
        super(gridPosition);
    }

    @Override
    protected int getDuration(){
        return switch (growingState.ordinal()) {
            case 0 -> 0;
            case 1 -> 2 + growthModifier;
            case 2 -> 3 + growthModifier;
            case 3 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + growingState.ordinal());
        };
    }

    @Override
    public ItemType getPlantType(){
        return ItemType.WHEAT;
    }
}
