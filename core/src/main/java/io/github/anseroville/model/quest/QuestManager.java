package io.github.anseroville.model.quest;

import io.github.anseroville.model.Wallet;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.enums.ItemType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class QuestManager {
    private final List<Quest> quests;
    private int activeQuestIndex;
    private final Wallet wallet;
    private final Inventory inventory;
    private Quest mainQuest;

    public QuestManager(Wallet wallet, Inventory inventory) {
        this.quests = createQuests();
        this.activeQuestIndex = 0;
        this.wallet = wallet;
        this.inventory = inventory;
    }

    private List<Quest> createQuests() {
        //todo: dalem jakies przykladowe questy zeby sprawdzic czy dziala
        List<Quest> createdQuests = new ArrayList<>();

        Map<ItemType, Integer> firstQuestRequirements = new EnumMap<>(ItemType.class);
        firstQuestRequirements.put(ItemType.CARROT, 3);

        createdQuests.add(new Quest(firstQuestRequirements, 10));

        Map<ItemType, Integer> secondQuestRequirements = new EnumMap<>(ItemType.class);
        secondQuestRequirements.put(ItemType.CARROT, 6);

        createdQuests.add(new Quest(secondQuestRequirements, 25));

        Map<ItemType, Integer> mainQuestRequirements = new EnumMap<>(ItemType.class);
        secondQuestRequirements.put(ItemType.CARROT, 6);

        mainQuest = new Quest(mainQuestRequirements, 100);

        return createdQuests;
    }

    public Quest getActiveQuest() {
        if (activeQuestIndex >= quests.size()) {
            return null;
        }

        return quests.get(activeQuestIndex);
    }

    public boolean completeMainQuest() {
        if (mainQuest == null || !mainQuest.canComplete(inventory)) {
            return false;
        }

        mainQuest.complete(inventory);
        wallet.add(mainQuest.getRewardMoney());

        return true;
    }

    public boolean completeActiveQuest() {
        Quest activeQuest = getActiveQuest();
        System.out.println("INDEKS: " + activeQuestIndex);

        if (activeQuest == null || !activeQuest.canComplete(inventory)) {
            return false;
        }

        activeQuest.complete(inventory);
        wallet.add(activeQuest.getRewardMoney());
        activeQuestIndex++;

        return true;
    }
}