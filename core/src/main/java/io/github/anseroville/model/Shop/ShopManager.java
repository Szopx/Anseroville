package io.github.anseroville.model.Shop;

import io.github.anseroville.model.Wallet;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.inventory.ItemType;

import java.util.EnumMap;
import java.util.Map;

public class ShopManager {
    private final Wallet wallet;
    private final Inventory inventory;
    private Shop currentShop;

    public ShopManager(Wallet wallet, Inventory inventory) {
        this.wallet = wallet;
        this.inventory = inventory;
        this.currentShop = createDefaultShop();
    }

    //przykladowe ceny wstepne
    //w ostatecznej wersji Shop ma konstruktor z mapy, shop manager moze trzymac liste map na konkretne poziomy
    private Shop createDefaultShop() {
        Map<ItemType, Integer> buyPrices = new EnumMap<>(ItemType.class);

        buyPrices.put(ItemType.POTATO_SEED, 5);
        buyPrices.put(ItemType.WHEAT_SEED, 3);

        Map<ItemType, Integer> sellPrices = new EnumMap<>(ItemType.class);

        sellPrices.put(ItemType.CARROT, 4);
        sellPrices.put(ItemType.POTATO, 8);

        return new Shop(buyPrices, sellPrices);
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    //pytanie czy tak to robic czy po prostu kupujemy zawsze jedną sztuke
    public boolean buyItem(ItemType type, int amount) {
        if (currentShop == null || amount <= 0) return false;

        int pricePerUnit = currentShop.getBuyPrice(type);
        if (pricePerUnit == -1) return false;

        int totalCost = pricePerUnit * amount;


        if (wallet.getMoney() < totalCost) {
            return false;
        }

        if (inventory.add(type, amount)) {
            wallet.sub(totalCost);
            return true;
        }

        return false;
    }

    public boolean sellItem(ItemType type, int amount) {
        if (currentShop == null || amount <= 0) return false;

        int pricePerUnit = currentShop.getSellPrice(type);
        if (pricePerUnit == -1) return false;

        if (!inventory.has(type, amount)) {
            return false;
        }

        inventory.remove(type, amount);
        wallet.add(pricePerUnit * amount);
        return true;
    }
}
