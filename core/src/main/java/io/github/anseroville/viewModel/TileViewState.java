package io.github.anseroville.viewModel;

import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;

public class TileViewState {
    private final int x;
    private final int y;
    private final boolean selected;
    private final GrowingState growingState;
    private final ItemType grows;

    public TileViewState(int x, int y, boolean selected, GrowingState growingstate,
                         ItemType grows) {
        this.x = x*75;
        this.y = y*75; //todo
        this.selected = selected;
        this.growingState = growingstate;
        this.grows = grows;
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
}