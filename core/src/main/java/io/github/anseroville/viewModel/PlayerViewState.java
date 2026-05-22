package io.github.anseroville.viewModel;

import io.github.anseroville.enums.Direction;

public class PlayerViewState {
    private final int x;
    private final int y;
    private final Direction direction;

    public PlayerViewState(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
}