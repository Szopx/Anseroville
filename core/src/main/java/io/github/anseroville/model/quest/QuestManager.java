package io.github.anseroville.model.quest;

import io.github.anseroville.model.Wallet;
import io.github.anseroville.model.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

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

    public Quest getActiveQuest() {
        if (activeQuestIndex >= quests.size()) {
            return null;
        }

        return quests.get(activeQuestIndex);
    }

    public Quest getMainQuest() {
        return mainQuest;
    }

    public int getActiveQuestIndex() {
        return activeQuestIndex;
    }

    public int getActiveLevelNumber() {
        return activeQuestIndex + 1;
    }

    public int getQuestsCount() {
        return quests.size();
    }

    public List<Quest> getQuests() {
        return new ArrayList<>(quests);
    }

    public boolean hasCompletedAllLevelQuests() {
        return activeQuestIndex >= quests.size();
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
}
