package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class QuestViewState {
    private final Map<ItemType, Integer> requiredItems;
    private final Map<ItemType, Integer> mainQuest;
    private final boolean activeQuestAvailable;
    private final int activeQuestReward;
    private final int activeLevelNumber;
    private final int maxLevelNumber;
    private final boolean gameFinished;

    public QuestViewState(
            boolean activeQuestAvailable,
            Map<ItemType, Integer> requiredItems,
            Map<ItemType, Integer> mainQuest,
            int activeQuestReward,
            int activeLevelNumber,
            int maxLevelNumber,
            boolean gameFinished
    ) {
        this.activeQuestAvailable = activeQuestAvailable;
        this.requiredItems = copyMap(requiredItems);
        this.mainQuest = copyMap(mainQuest);
        this.activeQuestReward = activeQuestReward;
        this.activeLevelNumber = activeLevelNumber;
        this.maxLevelNumber = maxLevelNumber;
        this.gameFinished = gameFinished;
    }

    private Map<ItemType, Integer> copyMap(Map<ItemType, Integer> source) {
        Map<ItemType, Integer> copy = new EnumMap<>(ItemType.class);
        copy.putAll(source);
        return Collections.unmodifiableMap(copy);
    }

    public Map<ItemType, Integer> getRequiredItems() {
        return requiredItems;
    }

    public Map<ItemType, Integer> getMainQuest() {
        return mainQuest;
    }

    public boolean isActiveQuestAvailable() {
        return activeQuestAvailable;
    }

    public int getActiveQuestRewardMoney() {
        return activeQuestReward;
    }

    public int getActiveLevelNumber() {
        return activeLevelNumber;
    }

    public int getMaxLevelNumber() {
        return maxLevelNumber;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }
}