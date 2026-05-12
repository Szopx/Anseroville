package io.github.anseroville.viewModel;

import io.github.anseroville.model.Tiles.GrowingState;

public class TileViewState {
    private final int x;
    private final int y;
    private final boolean selected;
    private GrowingState growingState;

    public TileViewState(int x, int y, boolean selected, GrowingState growingstate) {
        this.x = x*75;
        this.y = y*75; //todo
        this.selected = selected;
        this.growingState = growingstate;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSelected() {
        return selected;
    }
    public GrowingState getGrowingState() { return growingState; }
}