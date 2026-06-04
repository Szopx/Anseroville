package io.github.anseroville.model;

import io.github.anseroville.model.gambling.Machine;
import io.github.anseroville.model.inventory.InventoryData;
import io.github.anseroville.model.inventory.Wallet;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.tiles.TileData;

public class GameState {
    private final Player player;
    private final Inventory inventory;
    private final Hand hand;
    private final Wallet wallet;
    private final Machine machine;
    private final WorldState worldState;

    public GameState() {
        player = new Player(100, 100);
        inventory = new Inventory();
        hand = new Hand(inventory);
        wallet = new Wallet();
        machine = new Machine(wallet);
        worldState = new WorldState(TileData.createInteractableTiles());
    }

    public WorldState getWorldState() {
        return worldState;
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