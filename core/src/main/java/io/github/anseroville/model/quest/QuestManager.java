package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.inventory.Wallet;
import io.github.anseroville.model.inventory.Inventory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QuestManager {

    private final List<Quest> quests;
    private final Wallet wallet;
    private final Inventory inventory;
    private final Quest mainQuest;
    private int activeQuestIndex;

    public QuestManager(Wallet wallet, Inventory inventory) {
        this.wallet = wallet;
        this.inventory = inventory;
        this.quests = QuestData.createLevelQuests();
        this.mainQuest = QuestData.createMainQuest();
        this.activeQuestIndex = 0;
    }

    private Quest getActiveQuest() {
        if (activeQuestIndex >= quests.size()) {
            return null;
        }

        return quests.get(activeQuestIndex);
    }

    public boolean isActiveQuestAvailable() {
        return getActiveQuest()!=null;
    }

    public Map<ItemType, Integer> getActiveQuestRequiredItems(){
        if (isActiveQuestAvailable()){
            return getActiveQuest().getRequiredItems();
        }
        else {
            return Collections.emptyMap();
        }
    }

    public boolean hasCompletedAllLevelQuests() {
        return activeQuestIndex >= quests.size();
    }

    public Map<ItemType, Integer> getMainQuestRequiredItems(){
        return mainQuest.getRequiredItems();
    }

    public boolean completeActiveQuest() {
        Quest activeQuest = getActiveQuest();

        if (activeQuest == null || !activeQuest.canComplete(inventory)) {
            return false;
        }

        activeQuest.complete(inventory);
        wallet.add(activeQuest.getRewardMoney());
        activeQuestIndex++;
        return true;
    }

    public boolean completeMainQuest() {
        if (!hasCompletedAllLevelQuests()) {
            return false;
        }

        if (mainQuest == null || !mainQuest.canComplete(inventory)) {
            return false;
        }

        mainQuest.complete(inventory);
        wallet.add(mainQuest.getRewardMoney());
        return true;
    }

    public int getActiveQuestRewardMoney(){
        return getActiveQuest().getRewardMoney();
    }

    public int getMainQuestRewardMoney(){
        return mainQuest.getRewardMoney();
    }
}
