package io.github.anseroville.model.Shop;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.Wallet;
import io.github.anseroville.model.inventory.Inventory;

public class ShopManager {

    private final Wallet wallet;
    private final Inventory inventory;
    private Shop currentShop;

    public ShopManager(Wallet wallet, Inventory inventory) {
        this.wallet = wallet;
        this.inventory = inventory;
        this.currentShop = ShopData.createDefaultShop();
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(Shop currentShop) {
        this.currentShop = currentShop;
    }

    public boolean buyItem(ItemType type, int amount) {
        if (currentShop == null || type == null || amount <= 0) {
            return false;
        }

        int pricePerUnit = currentShop.getBuyPrice(type);
        if (pricePerUnit == -1) {
            return false;
        }

        int totalCost = pricePerUnit * amount;
        if (wallet.getMoney() < totalCost) {
            return false;
        }

        if (!inventory.add(type, amount)) {
            return false;
        }

        wallet.sub(totalCost);
        return true;
    }

    public boolean sellItem(ItemType type, int amount) {
        if (currentShop == null || type == null || amount <= 0) {
            return false;
        }

        int pricePerUnit = currentShop.getSellPrice(type);
        if (pricePerUnit == -1 || !inventory.has(type, amount)) {
            return false;
        }

        inventory.remove(type, amount);
        wallet.add(pricePerUnit * amount);
        return true;
    }
}
