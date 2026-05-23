package io.github.anseroville.model.Shop;

import io.github.anseroville.enums.ItemType;

import java.util.EnumMap;
import java.util.Map;

public final class ShopData {

    private ShopData() {
    }

    public static Shop createDefaultShop() {
        Map<ItemType, Integer> buyPrices = new EnumMap<>(ItemType.class);
        buyPrices.put(ItemType.CARROT_SEED, 2);
        buyPrices.put(ItemType.WHEAT_SEED, 3);
        buyPrices.put(ItemType.POTATO_SEED, 5);
        buyPrices.put(ItemType.CORN_SEED, 6);
        buyPrices.put(ItemType.TORCH, 8);
        buyPrices.put(ItemType.SHIELD, 20);

        Map<ItemType, Integer> sellPrices = new EnumMap<>(ItemType.class);
        sellPrices.put(ItemType.CARROT, 4);
        sellPrices.put(ItemType.WHEAT, 6);
        sellPrices.put(ItemType.POTATO, 9);
        sellPrices.put(ItemType.CORN, 11);

        return new Shop(buyPrices, sellPrices);
    }
}
