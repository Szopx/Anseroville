package io.github.anseroville.viewModel;

public class TileViewState {
    private final int x;
    private final int y;
    private final boolean selected;

    public TileViewState(int x, int y, boolean selected) {
        this.x = x*75;
        this.y = y*73; //todo
        this.selected = selected;
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
}