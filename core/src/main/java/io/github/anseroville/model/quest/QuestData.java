package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class QuestData {

    private QuestData() {
    }

    public static List<Quest> createLevelQuests() {
        List<Quest> quests = new ArrayList<>();

        quests.add(createQuest(
                15,
                requirement(ItemType.CARROT, 3)
        ));

        quests.add(createQuest(
                35,
                requirement(ItemType.CARROT, 6)
        ));

        quests.add(createQuest(
                60,
                requirement(ItemType.CARROT, 8),
                requirement(ItemType.WHEAT, 3)
        ));

        quests.add(createQuest(
                90,
                requirement(ItemType.WHEAT, 7),
                requirement(ItemType.POTATO, 4)
        ));

        quests.add(createQuest(
                130,
                requirement(ItemType.CARROT, 10),
                requirement(ItemType.POTATO, 8)
        ));

        quests.add(createQuest(
                180,
                requirement(ItemType.WHEAT, 10),
                requirement(ItemType.CORN, 8)
        ));

        quests.add(createQuest(
                240,
                requirement(ItemType.CARROT, 15),
                requirement(ItemType.WHEAT, 12),
                requirement(ItemType.POTATO, 10),
                requirement(ItemType.CORN, 10)
        ));

        return quests;
    }

    public static Quest createMainQuest() {
        return createQuest(
                400,
                requirement(ItemType.CARROT, 20),
                requirement(ItemType.WHEAT, 15),
                requirement(ItemType.POTATO, 15),
                requirement(ItemType.CORN, 12)
        );
    }

    private static Quest createQuest(int rewardMoney, QuestRequirement... requirements) {
        Map<ItemType, Integer> requiredItems = new EnumMap<>(ItemType.class);

        for (QuestRequirement requirement : requirements) {
            requiredItems.put(requirement.getItemType(), requirement.getAmount());
        }

        return new Quest(requiredItems, rewardMoney);
    }

    private static QuestRequirement requirement(ItemType itemType, int amount) {
        return new QuestRequirement(itemType, amount);
    }
}
