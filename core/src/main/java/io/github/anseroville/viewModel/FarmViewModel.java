package io.github.anseroville.viewModel;

import io.github.anseroville.model.Direction;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.InteractableTile;
import io.github.anseroville.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FarmViewModel {
    private static final int TILE_WIDTH = 75;
    private static final int TILE_HEIGHT = 73;
    private static final int PLAYER_REACH = 50;

    private final GameState gameState;
    private InteractableTile selectedTile;

    public FarmViewModel(GameState gameState) {
        this.gameState = gameState;
    }

    public void movePlayer(Direction direction) {
        gameState.getPlayer().move(direction);
        updateSelectedTile();
    }

    public void teleportPlayer() {
        gameState.getPlayer().teleportToStart();
        updateSelectedTile();
    }

    private void updateSelectedTile() {
        if (selectedTile != null) {
            selectedTile.unselect();
        }

        GridPosition lookedPosition = getLookedGridPosition();

        selectedTile = gameState.getInteractableTiles().get(lookedPosition);

        if (selectedTile != null) {
            selectedTile.select();
        }
    }

    private GridPosition getLookedGridPosition() {
        Player player = gameState.getPlayer();

        int lookedX = player.getX();
        int lookedY = player.getY();

        switch (player.getDirection()) {
            case UP -> lookedY += PLAYER_REACH;
            case DOWN -> lookedY -= PLAYER_REACH;
            case LEFT -> lookedX -= PLAYER_REACH;
            case RIGHT -> lookedX += PLAYER_REACH;
        }

        return new GridPosition(lookedX / TILE_WIDTH, lookedY / TILE_HEIGHT);
    }

    public PlayerViewState getPlayerViewState() {
        Player player = gameState.getPlayer();

        return new PlayerViewState(
                player.getX(),
                player.getY(),
                player.getDirection()
        );
    }

    public List<TileViewState> getTileViewStates() {
        List<TileViewState> tileViewStates = new ArrayList<>();

        for (Map.Entry<GridPosition, InteractableTile> entry : gameState.getInteractableTiles().entrySet()) {
            InteractableTile tile = entry.getValue();

            tileViewStates.add(new TileViewState(
                    tile.getGridPosition().getX(),tile.getGridPosition().getY(),tile.isSelected())
            );
        }

        return tileViewStates;
    }
}