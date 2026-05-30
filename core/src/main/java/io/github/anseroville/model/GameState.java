package io.github.anseroville.model;

import io.github.anseroville.model.gambling.Machine;
import io.github.anseroville.model.tiles.InteractableTile;
import io.github.anseroville.model.data.GameData;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.systems.CropGrowthSystem;
import io.github.anseroville.model.systems.NightManager;
import io.github.anseroville.model.systems.PlantingManager;
import io.github.anseroville.model.systems.TileManager;
import io.github.anseroville.model.time.DayNightCycle;

import java.util.Map;
import java.util.Random;

public class GameState {
    private static final float DAY_DURATION = 30f;
    private static final float NIGHT_DURATION = 10f;
    private static final int NIGHT_DESTROY_CHANCE = 5;

    private final Player player;
    private final Inventory inventory;
    private final Hand hand;
    private final Wallet wallet;
    private final Machine machine;

    private final TileManager tileManager;
    private final PlantingManager plantingManager;
    private final CropGrowthSystem cropGrowthSystem;
    private final NightManager nightManager;

    public GameState() {
        this.player = new Player(100, 100);

        this.inventory = new Inventory();
        GameData.addStartingInventory(inventory);

        this.hand = new Hand(inventory);

        this.wallet = new Wallet();
        this.wallet.add(GameData.STARTING_MONEY);
        this.machine = new Machine(wallet);

        this.tileManager = new TileManager(GameData.createInteractableTiles());
        this.plantingManager = new PlantingManager(tileManager, hand, inventory);
        this.cropGrowthSystem = new CropGrowthSystem(tileManager);
        this.nightManager = new NightManager(
                tileManager,
                inventory,
                new DayNightCycle(DAY_DURATION, NIGHT_DURATION),
                new Random(),
                NIGHT_DESTROY_CHANCE
        );
    }

    public Player getPlayer() {
        return player;
    }

    public Map<GridPosition, InteractableTile> getInteractableTiles() {
        return tileManager.getInteractableTiles();
    }

    public InteractableTile getTile(GridPosition position) {
        return tileManager.getTile(position);
    }

    public void modifyTile(GridPosition position, InteractableTile interactableTile) {
        tileManager.modifyTile(position, interactableTile);
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

    public void plant(InteractableTile selectedTile) {
        plantingManager.plant(selectedTile);
    }

    public void water(InteractableTile selectedTile) {
        plantingManager.water(selectedTile);
    }

    public void update(float delta) {
        cropGrowthSystem.update(delta);
        nightManager.update(delta);
    }

    public boolean isNight() {
        return nightManager.isNight();
    }

    public boolean hasTorch() {
        return nightManager.hasTorch();
    }

    public boolean isNightWithoutTorch() {
        return nightManager.isNightWithoutTorch();
    }

    public float getNightRemainingTime() {
        return nightManager.getNightRemainingTime();
    }

    public Machine getMachine() {
        return machine;
    }
}

