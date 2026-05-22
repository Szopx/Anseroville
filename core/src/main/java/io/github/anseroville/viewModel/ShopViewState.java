package io.github.anseroville.viewModel;

import io.github.anseroville.enums.ItemType;

import java.util.Collections;
import java.util.Map;

public class ShopViewState {
    private final Map<ItemType, Integer> buyPrices;
    private final Map<ItemType, Integer> sellPrices;

    public ShopViewState(Map<ItemType, Integer> buyPrices, Map<ItemType, Integer> sellPrices) {
        this.buyPrices = Collections.unmodifiableMap(buyPrices);
        this.sellPrices = Collections.unmodifiableMap(sellPrices);
    }

    public Map<ItemType, Integer> getBuyPrices() {
        return buyPrices;
    }

    public Map<ItemType, Integer> getSellPrices() {
        return sellPrices;
    }
}
