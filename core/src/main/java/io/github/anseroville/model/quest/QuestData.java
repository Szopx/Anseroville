package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class QuestData {
    private QuestData() {
    }

    public static List<Level> createLevels() {
        List<Level> levels = new ArrayList<>();

        levels.add(createLevelOne());
        levels.add(createLevelTwo());
        levels.add(createLevelThree());
        levels.add(createLevelFour());

        return levels;
    }

    /// ////////////////////////

    private static LevelStartState createLevelOneStartState() {
        Map<ItemType, Integer> inventoryItems = new EnumMap<>(ItemType.class);
        inventoryItems.put(ItemType.CARROT_SEED, 10);
        inventoryItems.put(ItemType.WHEAT_SEED, 6);
        inventoryItems.put(ItemType.TORCH, 2);

        return new LevelStartState(
                20,
                inventoryItems,
                createLevelOneBuyPrices(),
                createLevelOneSellPrices()
        );
    }

    private static LevelStartState createLevelTwoStartState() {
        Map<ItemType, Integer> inventoryItems = new EnumMap<>(ItemType.class);
        inventoryItems.put(ItemType.CARROT_SEED, 5);
        inventoryItems.put(ItemType.WHEAT_SEED, 10);
        inventoryItems.put(ItemType.POTATO_SEED, 5);
        inventoryItems.put(ItemType.TORCH, 1);

        return new LevelStartState(
                35,
                inventoryItems,
                createLevelTwoBuyPrices(),
                createLevelTwoSellPrices()
        );
    }

    private static LevelStartState createLevelThreeStartState() {
        Map<ItemType, Integer> inventoryItems = new EnumMap<>(ItemType.class);
        inventoryItems.put(ItemType.POTATO_SEED, 10);
        inventoryItems.put(ItemType.CORN_SEED, 8);
        inventoryItems.put(ItemType.TORCH, 2);

        return new LevelStartState(
                50,
                inventoryItems,
                createLevelThreeBuyPrices(),
                createLevelThreeSellPrices()
        );
    }

    private static LevelStartState createLevelFourStartState() {
        Map<ItemType, Integer> inventoryItems = new EnumMap<>(ItemType.class);
        inventoryItems.put(ItemType.CARROT_SEED, 8);
        inventoryItems.put(ItemType.WHEAT_SEED, 8);
        inventoryItems.put(ItemType.POTATO_SEED, 8);
        inventoryItems.put(ItemType.CORN_SEED, 8);
        inventoryItems.put(ItemType.TORCH, 3);
        inventoryItems.put(ItemType.SHIELD, 1);

        return new LevelStartState(
                75,
                inventoryItems,
                createLevelFourBuyPrices(),
                createLevelFourSellPrices()
        );
    }







    private static Map<ItemType, Integer> createLevelOneBuyPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT_SEED, 2);
        prices.put(ItemType.WHEAT_SEED, 3);
        prices.put(ItemType.TORCH, 8);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelOneSellPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT, 4);
        prices.put(ItemType.WHEAT, 6);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelTwoBuyPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT_SEED, 2);
        prices.put(ItemType.WHEAT_SEED, 3);
        prices.put(ItemType.POTATO_SEED, 5);
        prices.put(ItemType.TORCH, 8);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelTwoSellPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT, 4);
        prices.put(ItemType.WHEAT, 6);
        prices.put(ItemType.POTATO, 9);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelThreeBuyPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.WHEAT_SEED, 3);
        prices.put(ItemType.POTATO_SEED, 5);
        prices.put(ItemType.CORN_SEED, 6);
        prices.put(ItemType.TORCH, 8);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelThreeSellPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.WHEAT, 6);
        prices.put(ItemType.POTATO, 9);
        prices.put(ItemType.CORN, 11);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelFourBuyPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT_SEED, 2);
        prices.put(ItemType.WHEAT_SEED, 3);
        prices.put(ItemType.POTATO_SEED, 5);
        prices.put(ItemType.CORN_SEED, 6);
        prices.put(ItemType.TORCH, 8);
        prices.put(ItemType.SHIELD, 20);
        return prices;
    }

    private static Map<ItemType, Integer> createLevelFourSellPrices() {
        Map<ItemType, Integer> prices = new EnumMap<>(ItemType.class);
        prices.put(ItemType.CARROT, 4);
        prices.put(ItemType.WHEAT, 6);
        prices.put(ItemType.POTATO, 9);
        prices.put(ItemType.CORN, 11);
        return prices;
    }







    private static Level createLevelOne() {
        List<Quest> sideQuests = new ArrayList<>();

        sideQuests.add(createQuest(
                15,
                requirement(ItemType.CARROT, 3)
        ));

        sideQuests.add(createQuest(
                35,
                requirement(ItemType.CARROT, 6)
        ));

        Quest mainQuest = createQuest(
                100,
                requirement(ItemType.CARROT, 10),
                requirement(ItemType.WHEAT, 5)
        );

        return new Level(1, createLevelOneStartState(), sideQuests, mainQuest);
    }

    private static Level createLevelTwo() {
        List<Quest> sideQuests = new ArrayList<>();

        sideQuests.add(createQuest(
                60,
                requirement(ItemType.WHEAT, 8)
        ));

        sideQuests.add(createQuest(
                80,
                requirement(ItemType.POTATO, 6)
        ));

        Quest mainQuest = createQuest(
                180,
                requirement(ItemType.WHEAT, 12),
                requirement(ItemType.POTATO, 10)
        );

        return new Level(2, createLevelTwoStartState(), sideQuests, mainQuest);
    }

    private static Level createLevelThree() {
        List<Quest> sideQuests = new ArrayList<>();

        sideQuests.add(createQuest(
                120,
                requirement(ItemType.CORN, 8)
        ));

        sideQuests.add(createQuest(
                150,
                requirement(ItemType.POTATO, 12),
                requirement(ItemType.CORN, 6)
        ));

        Quest mainQuest = createQuest(
                300,
                requirement(ItemType.CARROT, 15),
                requirement(ItemType.POTATO, 15),
                requirement(ItemType.CORN, 12)
        );

        return new Level(3, createLevelThreeStartState(), sideQuests, mainQuest);
    }

    private static Level createLevelFour() {
        List<Quest> sideQuests = new ArrayList<>();

        sideQuests.add(createQuest(
                200,
                requirement(ItemType.CARROT, 20),
                requirement(ItemType.WHEAT, 15)
        ));

        sideQuests.add(createQuest(
                250,
                requirement(ItemType.POTATO, 18),
                requirement(ItemType.CORN, 18)
        ));

        Quest mainQuest = createQuest(
                500,
                requirement(ItemType.CARROT, 25),
                requirement(ItemType.WHEAT, 20),
                requirement(ItemType.POTATO, 20),
                requirement(ItemType.CORN, 20)
        );

        return new Level(4, createLevelFourStartState(), sideQuests, mainQuest);
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