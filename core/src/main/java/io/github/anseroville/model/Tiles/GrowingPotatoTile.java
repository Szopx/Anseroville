package io.github.anseroville.model.Tiles;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;

import java.util.concurrent.ThreadLocalRandom;

public class GrowingPotatoTile extends GrowingGroundTile{
    private final int growthModifier = ThreadLocalRandom.current().nextInt(-3, 7);

    public GrowingPotatoTile(EmptyGroundTile emptyGroundTile) {
        super(emptyGroundTile);
    }

    GrowingPotatoTile(GridPosition gridPosition){
        super(gridPosition);
    }

    @Override
    protected int getDuration(){
        return switch (growingState.ordinal()) {
            case 0 -> 0;
            case 1 -> 5 + growthModifier;
            case 2 -> 7 + growthModifier;
            case 3 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + growingState.ordinal());
        };
    }

    @Override
    public ItemType getPlantType(){
        return ItemType.POTATO;
    }
}
