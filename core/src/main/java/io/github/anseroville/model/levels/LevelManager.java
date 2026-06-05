package io.github.anseroville.model.levels;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.shop.ShopManager;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;

import java.util.List;
import java.util.Map;

public class LevelManager {
    private final List<Level> levels;
    private int activeLevelIndex;
    private boolean gameFinished;
    private boolean initialized;
    private final GameState gameState;
    private final ShopManager shopManager;

    public LevelManager(GameState gameState, ShopManager shopManager) {
        this.levels = LevelData.createLevels();
        this.activeLevelIndex = 0;
        this.gameFinished = false;
        this.initialized = false;
        this.gameState = gameState;
        this.shopManager = shopManager;
    }

    public void initializeCurrentLevel() {
        if (initialized) {
            return;
        }

        applyActiveLevelStartState();
        initialized = true;
    }

    public Level getActiveLevel() {
        if (gameFinished || activeLevelIndex >= levels.size()) {
            return null;
        }

        return levels.get(activeLevelIndex);
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public int getActiveLevelNumber() {
        if (gameFinished) {
            return levels.size();
        }

        return activeLevelIndex + 1;
    }

    public int getMaxLevelNumber() {
        return levels.size();
    }

    public LevelStartState getActiveLevelStartState() {
        Level activeLevel = getActiveLevel();

        if (activeLevel == null) {
            return null;
        }

        return activeLevel.getStartState();
    }

    private void applyActiveLevelStartState() {
        LevelStartState startState = getActiveLevelStartState();

        if (startState == null) {
            return;
        }

        resetInventory(startState);
        resetWallet(startState);
        resetHand();
        resetShop(startState);
        resetWorld();
        resetPlayer();
    }

    public void startNextLevel()
    {
        if (activeLevelIndex >= levels.size() - 1) {
            gameFinished = true;
        }

        activeLevelIndex++;
        applyActiveLevelStartState();
    }

    private void resetInventory(LevelStartState startState) {
        gameState.getInventory().clear();

        for (Map.Entry<ItemType, Integer> entry : startState.getInventoryItems().entrySet()) {
            gameState.getInventory().add(entry.getKey(), entry.getValue());
        }
    }

    private void resetWallet(LevelStartState startState) {
        gameState.getWallet().setMoney(startState.getMoney());
    }

    private void resetHand() {
        gameState.getHand().clear();
    }

    private void resetShop(LevelStartState startState) {
        shopManager.setCurrentShop(startState.createShop());
    }

    private void resetWorld() {
        for (Map.Entry<GridPosition, InteractableTile> tile : gameState.getWorldState().getTilesView().entrySet()) {
            if (tile.getValue() instanceof GroundTile) {
                ((GroundTile) tile.getValue()).clearCrop();
            }
        }
    }

    private void resetPlayer() {
        gameState.getPlayer().teleportToStart();
    }
}
