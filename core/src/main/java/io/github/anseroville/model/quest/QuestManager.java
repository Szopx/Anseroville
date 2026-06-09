package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.levels.LevelManager;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class QuestManager {
    private final GameState gameState;
    private final LevelManager levelManager;
    private int activeSideQuestIndex;

    public QuestManager(GameState gameState, LevelManager levelManager) {
        this.gameState = gameState;
        this.levelManager = levelManager;
        this.activeSideQuestIndex = 0;
    }

    private Quest getActiveSideQuest() {
        if (levelManager.getActiveLevel() == null) {
            return null;
        }

        if (activeSideQuestIndex >= levelManager.getActiveLevel().getSideQuestsCount()) {
            return null;
        }

        return levelManager.getActiveLevel().getSideQuests().get(activeSideQuestIndex);
    }

    private Quest getActiveMainQuest() {
        if (levelManager.getActiveLevel() == null) {
            return null;
        }

        return levelManager.getActiveLevel().getMainQuest();
    }

    public boolean isActiveQuestAvailable() {
        return getActiveSideQuest() != null;
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

    public boolean completeActiveQuest() {
        Quest activeSideQuest = getActiveSideQuest();

        if (activeSideQuest == null || !activeSideQuest.canComplete(gameState.getInventory())) {
            return false;
        }

        activeSideQuest.complete(gameState.getInventory());
        gameState.getWallet().add(activeSideQuest.getRewardMoney());
        activeSideQuestIndex++;

        return true;
    }

    public boolean completeMainQuest() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null || !activeMainQuest.canComplete(gameState.getInventory())) {
            return false;
        }

        activeMainQuest.complete(gameState.getInventory());
        gameState.getWallet().add(activeMainQuest.getRewardMoney());

        return true;
    }

    public void getItemsForMainQuest() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null) {
            return;
        }

        activeMainQuest.getItemsToComplete(gameState.getInventory());

    }

    public void skipMainQuest() {
        Quest activeMainQuest = getActiveMainQuest();

        if (activeMainQuest == null) {
            return;
        }

        activeMainQuest.getItemsToComplete(gameState.getInventory());
        activeMainQuest.complete(gameState.getInventory());
        gameState.getWallet().add(activeMainQuest.getRewardMoney());

    }

    public void resetSideQuestProgress() {
        activeSideQuestIndex = 0;
    }

    public static Quest createQuest(int rewardMoney, QuestRequirement... requirements) {
        Map<ItemType, Integer> requiredItems = new EnumMap<>(ItemType.class);

        for (QuestRequirement requirement : requirements) {
            requiredItems.put(requirement.getItemType(), requirement.getAmount());
        }

        return new Quest(requiredItems, rewardMoney);
    }

    public static QuestRequirement requirement(ItemType itemType, int amount) {
        return new QuestRequirement(itemType, amount);
    }
}