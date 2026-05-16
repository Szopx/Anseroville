package io.github.anseroville.model.Shop;

import io.github.anseroville.model.inventory.ItemType;

import java.util.EnumMap;
import java.util.Map;

public class Shop {
    private final Map<ItemType, Integer> buyPrices;
    private final Map<ItemType, Integer> sellPrices;

    public Shop(Map<ItemType, Integer> buyPrices, Map<ItemType, Integer> sellPrices) {
        this.buyPrices = new EnumMap<>(ItemType.class);
        this.buyPrices.putAll(buyPrices);

        this.sellPrices = new EnumMap<>(ItemType.class);
        this.sellPrices.putAll(sellPrices);
    }

    public int getBuyPrice(ItemType type) {
        return buyPrices.getOrDefault(type, -1);
    }

    public int getSellPrice(ItemType type) {
        return sellPrices.getOrDefault(type, -1);
    }

    public Map<ItemType, Integer> getBuyPrices() {
        return buyPrices;
    }

    public Map<ItemType, Integer> getSellPrices() {
        return sellPrices;
    }
}
