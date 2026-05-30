package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Map;

public class QuestViewState {
    private final Map<ItemType, Integer> requiredItems;
    private final Map<ItemType, Integer> mainQuest;
    private final boolean isAvailable;
    private final int activeQuestReward;
    private final int mainQuestReward;

    public QuestViewState(boolean isAvailable,
                          Map<ItemType, Integer> requiredItems,
                          Map<ItemType, Integer> mainQuest,
                          int activeQuestReward,
                          int mainQuestReward){

        this.isAvailable=isAvailable;
        this.requiredItems=Map.copyOf(requiredItems);
        this.mainQuest=mainQuest;
        this.activeQuestReward=activeQuestReward;
        this.mainQuestReward=mainQuestReward;
    }
    public Map<ItemType, Integer> getRequiredItems(){
        return requiredItems;
    }
    public Map<ItemType, Integer> getMainQuest(){
        return mainQuest;
    }
    public boolean isActiveQuestAvailable(){
        return isAvailable;
    }

    public int getActiveQuestRewardMoney(){
        return activeQuestReward;
    }

    public int getMainQuestRewardMoney(){
        return mainQuestReward;
    }
}
