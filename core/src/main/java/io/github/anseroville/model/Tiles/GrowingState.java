package io.github.anseroville.model.Tiles;

public enum GrowingState {
    ZERO,
    SMALL,
    MEDIUM,
    LARGE;

    public GrowingState next() {
        GrowingState[] growingLevels = GrowingState.values();
        if(this.ordinal() == growingLevels.length - 1)
            return LARGE;
        return growingLevels[(this.ordinal() + 1)];
    }

    public boolean canBeCollected() {
        return this.ordinal() == GrowingState.values().length - 1;
    }

    public float getTimeToNextState() {
        return 3; //todo
    }
}
