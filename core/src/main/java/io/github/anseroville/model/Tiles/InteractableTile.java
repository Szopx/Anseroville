package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;

public abstract class InteractableTile {
    private final GridPosition gridPosition;

    private boolean selected;

    public InteractableTile(GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

    public GridPosition getGridPosition() {
        return gridPosition;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }
}