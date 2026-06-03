package io.github.anseroville.model.quest;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.shop.Shop;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class LevelStartState {
    private final int money;
    private final Map<ItemType, Integer> inventoryItems;
    private final Map<ItemType, Integer> buyPrices;
    private final Map<ItemType, Integer> sellPrices;

    public LevelStartState(
            int money,
            Map<ItemType, Integer> inventoryItems,
            Map<ItemType, Integer> buyPrices,
            Map<ItemType, Integer> sellPrices
    ) {
        this.money = money;
        this.inventoryItems = copyMap(inventoryItems);
        this.buyPrices = copyMap(buyPrices);
        this.sellPrices = copyMap(sellPrices);
    }

    private Map<ItemType, Integer> copyMap(Map<ItemType, Integer> source) {
        Map<ItemType, Integer> copy = new EnumMap<>(ItemType.class);
        copy.putAll(source);
        return copy;
    }

    public int getMoney() {
        return money;
    }

    public Map<ItemType, Integer> getInventoryItems() {
        return Collections.unmodifiableMap(inventoryItems);
    }

    public Shop createShop() {
        return new Shop(buyPrices, sellPrices);
    }
}