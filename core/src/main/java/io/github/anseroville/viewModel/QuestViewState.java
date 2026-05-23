package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Map;

public class QuestViewState {
    private final Map<ItemType, Integer> requiredItems;
    private final boolean isAvailable;
    public QuestViewState(boolean isAvailable, Map<ItemType, Integer> requiredItems){
        this.isAvailable=isAvailable;
        this.requiredItems=Map.copyOf(requiredItems);
    }
    public Map<ItemType, Integer> getRequiredItems(){
        return requiredItems;
    }
    public boolean isActiveQuestAvailable(){
        return isAvailable;
    }
}
