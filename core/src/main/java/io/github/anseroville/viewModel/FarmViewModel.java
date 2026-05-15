package io.github.anseroville.viewModel;

import io.github.anseroville.model.*;
import io.github.anseroville.model.Tiles.*;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.inventory.ItemType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FarmViewModel {
    private static final int TILE_WIDTH = 75;
    private static final int TILE_HEIGHT = 75;
    private static final int PLAYER_REACH = 50;

    private final GameState gameState;
    private InteractableTile selectedTile;
    private final Collector collector;

    public FarmViewModel(GameState gameState, Collector collector) {
        this.gameState = gameState;
        this.collector = collector;
    }

    public void movePlayer(Direction direction) {
        gameState.getPlayer().move(direction);
        updateSelectedTile();
    }

    public void teleportPlayer() {
        gameState.getPlayer().teleportToStart();
        updateSelectedTile();
    }

    public void plant() {
        gameState.plant(selectedTile);
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
                    tile.getGridPosition().getX(),tile.getGridPosition().getY(),tile.isSelected(), tile.getGrowingState())
            );
        }

        return tileViewStates;
    }

    public InventoryViewState getInventoryViewState(){
        Inventory inventory = gameState.getInventory();
        Hand hand = gameState.getHand();

        Map<ItemType, Integer> stateMap = new EnumMap<>(ItemType.class);

        for (ItemType type : ItemType.values()) {
            stateMap.put(type, inventory.getAmount(type));
        }

        return new InventoryViewState(stateMap,hand.getType(),hand.getAmount());
    }

    public void update(float delta) {
        gameState.update(delta);
    }

    public void collect() {
        if(!collector.collect(selectedTile)) {
            System.out.println("nie udało się zebrać warzywa");
        }
    }
}