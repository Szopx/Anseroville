package io.github.anseroville.viewModel;

public class GameEndViewState {
    private final boolean open;

    public GameEndViewState(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}