package io.github.anseroville.model;

import io.github.anseroville.model.gambling.Machine;
import io.github.anseroville.model.inventory.InventoryData;
import io.github.anseroville.model.inventory.Wallet;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;

public class GameState {
    private static final float DAY_DURATION = 30f;
    private static final float NIGHT_DURATION = 10f;
    private static final int NIGHT_DESTROY_CHANCE = 5;

    private final Player player;
    private final Inventory inventory;
    private final Hand hand;
    private final Wallet wallet;
    private final Machine machine;

    public GameState() {
        this.player = new Player(100, 100);

        this.inventory = new Inventory();
        InventoryData.addStartingInventory(inventory);

        this.hand = new Hand(inventory);

        this.wallet = new Wallet();
        this.wallet.add(InventoryData.STARTING_MONEY);
        this.machine = new Machine(wallet);
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Hand getHand() {
        return hand;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public Machine getMachine() {
        return machine;
    }
}

