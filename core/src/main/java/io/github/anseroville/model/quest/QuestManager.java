package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.inventory.Wallet;
import io.github.anseroville.model.shop.ShopManager;
import io.github.anseroville.model.systems.TileManager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QuestManager {
    private final GameState gameState;
    private final Inventory inventory;
    private final Wallet wallet;
    private final ShopManager shopManager;
    private final TileManager tileManager;
    private final List<Level> levels;

    private int activeLevelIndex;
    private int activeSideQuestIndex;
    private boolean gameFinished;
    private boolean initialized;

    public QuestManager(
            GameState gameState,
            ShopManager shopManager,
            TileManager tileManager
    ) {
        this.gameState = gameState;
        this.inventory = gameState.getInventory();
        this.wallet = gameState.getWallet();
        this.shopManager = shopManager;
        this.tileManager = tileManager;
        this.levels = QuestData.createLevels();
        this.activeLevelIndex = 0;
        this.activeSideQuestIndex = 0;
        this.gameFinished = false;
        this.initialized = false;
    }

    public void initializeCurrentLevel() {
        if (initialized) {
            return;
        }

        applyActiveLevelStartState();
        initialized = true;
    }

    private Level getActiveLevel() {
        if (gameFinished || activeLevelIndex >= levels.size()) {
            return null;
        }

        return levels.get(activeLevelIndex);
    }

    private Quest getActiveSideQuest() {
        Level activeLevel = getActiveLevel();

        if (activeLevel == null) {
            return null;
        }

        if (activeSideQuestIndex >= activeLevel.getSideQuestsCount()) {
            return null;
        }

        return activeLevel.getSideQuests().get(activeSideQuestIndex);
    }

    private Quest getActiveMainQuest() {
        Level activeLevel = getActiveLevel();

        if (activeLevel == null) {
            return null;
        }

        return activeLevel.getMainQuest();
    }

    public boolean isActiveQuestAvailable() {
        return getActiveSideQuest() != null;
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

    public int getActiveSideQuestNumber() {
        if (!isActiveQuestAvailable()) {
            return 0;
        }

        return activeSideQuestIndex + 1;
    }

    public int getSideQuestsCount() {
        Level activeLevel = getActiveLevel();

        if (activeLevel == null) {
            return 0;
        }

        return activeLevel.getSideQuestsCount();
    }

    public Map<ItemType, Integer> getActiveQuestRequiredItems() {
        Quest activeSideQuest = getActiveSideQuest();

        if (activeSideQuest == null) {
            return Collections.emptyMap();
        }

        return activeSideQuest.getRequiredItems();
    }

    public Map<ItemType, Integer> getMainQuestRequiredItems() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null) {
            return Collections.emptyMap();
        }

        return activeMainQuest.getRequiredItems();
    }

    public int getActiveQuestRewardMoney() {
        Quest activeSideQuest = getActiveSideQuest();

        if (activeSideQuest == null) {
            return 0;
        }

        return activeSideQuest.getRewardMoney();
    }

    public int getMainQuestRewardMoney() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null) {
            return 0;
        }

        return activeMainQuest.getRewardMoney();
    }

    public boolean completeActiveQuest() {
        Quest activeSideQuest = getActiveSideQuest();

        if (activeSideQuest == null || !activeSideQuest.canComplete(inventory)) {
            return false;
        }

        activeSideQuest.complete(inventory);
        wallet.add(activeSideQuest.getRewardMoney());
        activeSideQuestIndex++;

        return true;
    }

    public boolean completeMainQuest() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null || !activeMainQuest.canComplete(inventory)) {
            return false;
        }

        activeMainQuest.complete(inventory);
        wallet.add(activeMainQuest.getRewardMoney());

        if (activeLevelIndex >= levels.size() - 1) {
            gameFinished = true;
            return true;
        }

        activeLevelIndex++;
        activeSideQuestIndex = 0;

        applyActiveLevelStartState();

        return true;
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

    private void resetInventory(LevelStartState startState) {
        inventory.clear();

        for (Map.Entry<ItemType, Integer> entry : startState.getInventoryItems().entrySet()) {
            inventory.add(entry.getKey(), entry.getValue());
        }
    }

    private void resetWallet(LevelStartState startState) {
        wallet.setMoney(startState.getMoney());
    }

    private void resetHand() {
        gameState.getHand().clear();
    }

    private void resetShop(LevelStartState startState) {
        shopManager.setCurrentShop(startState.createShop());
    }

    private void resetWorld() {
        tileManager.resetGroundTiles();
    }

    private void resetPlayer() {
        gameState.getPlayer().teleportToStart();
    }
}