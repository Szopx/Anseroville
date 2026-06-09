package io.github.anseroville.model.shop;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.inventory.Wallet;
import io.github.anseroville.model.inventory.Inventory;

import java.util.Collections;
import java.util.Map;

public class ShopManager {

    private final Wallet wallet;
    private final Inventory inventory;
    private Shop currentShop;

    public ShopManager(Wallet wallet, Inventory inventory) {
        this.wallet = wallet;
        this.inventory = inventory;
        this.currentShop = new Shop(Collections.emptyMap(),Collections.emptyMap());
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    public boolean isCurrentShopAvailable() {
        if (getCurrentShop() == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public void setCurrentShop(Shop currentShop) {
        this.currentShop = currentShop;
    }

    public Map<ItemType, Integer> getCurrentShopBuyPrices() {
        if (isCurrentShopAvailable()) {
            return currentShop.getBuyPrices();
        }
        else {
            return Collections.emptyMap();
        }
    }

    public Map<ItemType, Integer> getCurrentShopSellPrices() {
        if (isCurrentShopAvailable()) {
            return currentShop.getSellPrices();
        }
        else {
            return Collections.emptyMap();
        }
    }

    public void buyItem(ItemType type) {
        if (currentShop == null || type == null) {
            return;
        }

        int price = currentShop.getBuyPrice(type);
        if (price == -1) {
            return;
        }

        if (wallet.getMoney() < price) {
            return;
        }

        if (!inventory.add(type, 1)) {
            return;
        }

        wallet.sub(price);
    }

    public void sellItem(ItemType type) {
        if (currentShop == null || type == null) {
            return;
        }

        int price = currentShop.getSellPrice(type);
        if (price == -1 || !inventory.has(type, 1)) {
            return;
        }

        inventory.remove(type, 1);
        wallet.add(price);
    }
}
