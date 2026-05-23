package io.github.anseroville.model;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.Shop.ShopManager;
import io.github.anseroville.model.Tiles.EmptyGroundTile;
import io.github.anseroville.model.Tiles.GrowingCarrotTile;
import io.github.anseroville.model.Tiles.GrowingCornTile;
import io.github.anseroville.model.Tiles.GrowingGroundTile;
import io.github.anseroville.model.Tiles.GrowingPotatoTile;
import io.github.anseroville.model.Tiles.GrowingWheatTile;
import io.github.anseroville.model.Tiles.InteractableTile;
import io.github.anseroville.model.data.GameData;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.quest.QuestManager;
import io.github.anseroville.model.time.DayNightCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameState {
    private static final float DAY_DURATION = 30f;
    private static final float NIGHT_DURATION = 10f;
    private static final int NIGHT_DESTROY_CHANCE = 5;

    private final Player player;
    private final Map<GridPosition, InteractableTile> interactableTiles;
    private final Inventory inventory;
    private final Hand hand;
    private final Wallet wallet;
    private final QuestManager questManager;
    private final ShopManager shopManager;
    private final DayNightCycle dayNightCycle;
    private final Random random;

    private boolean nightEffectApplied = false;
    private boolean isTorchThisNight = false;

    public GameState() {
        this.player = new Player(100, 100);

        this.inventory = new Inventory();
        GameData.addStartingInventory(inventory);

        this.hand = new Hand(inventory);

        this.wallet = new Wallet();
        this.wallet.add(GameData.STARTING_MONEY);

        this.questManager = new QuestManager(wallet, inventory);
        this.shopManager = new ShopManager(wallet, inventory);

        this.dayNightCycle = new DayNightCycle(DAY_DURATION, NIGHT_DURATION);
        this.random = new Random();
        this.interactableTiles = GameData.createInteractableTiles();
    }

    public Player getPlayer() {
        return player;
    }

    public Map<GridPosition, InteractableTile> getInteractableTiles() {
        return interactableTiles;
    }

    public InteractableTile getTile(GridPosition position) {
        return interactableTiles.get(position);
    }

    public void modifyTile(GridPosition position, InteractableTile interactableTile) {
        interactableTiles.put(position, interactableTile);
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

    public QuestManager getQuestManager() {
        return questManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public void toggleHand(ItemType clickedType) {

        if (!hand.isEmpty()) {
            ItemType typeInHand = hand.getType();
            hand.clear();

            if (typeInHand == clickedType) {
                return;
            }
        }

        int amountInInventory = inventory.getAmount(clickedType);
        if (amountInInventory > 0) {
            hand.set(clickedType);
        }
    }

    //todo ogarnijcie sobie i poprawcie
    public void plant(InteractableTile selectedTile) {
        if (selectedTile != null && selectedTile instanceof EmptyGroundTile) {
            EmptyGroundTile emptyGroundTile = (EmptyGroundTile) selectedTile;

            if (hand.getType() != null && hand.getAmount()>0) {
                if (hand.getType() == ItemType.CARROT_SEED) {
                    System.out.println("posadz marchewki");
                    GrowingCarrotTile carrotTile = new GrowingCarrotTile(emptyGroundTile);
                    interactableTiles.remove(emptyGroundTile.getGridPosition());
                    interactableTiles.put(carrotTile.getGridPosition(), carrotTile);
                    carrotTile.update((float)0.1);
                    inventory.remove(ItemType.CARROT_SEED,1);
                }
                else if (hand.getType() == ItemType.POTATO_SEED) {
                    System.out.println("posadz ziemniaki");
                    GrowingPotatoTile potatoTile = new GrowingPotatoTile(emptyGroundTile);
                    interactableTiles.remove(emptyGroundTile.getGridPosition());
                    interactableTiles.put(potatoTile.getGridPosition(), potatoTile);
                    potatoTile.update((float)0.1);
                    inventory.remove(ItemType.POTATO_SEED,1);
                }
                else if (hand.getType() == ItemType.CORN_SEED) {
                    System.out.println("posadz kukurydze");
                    GrowingCornTile cornTile = new GrowingCornTile(emptyGroundTile);
                    interactableTiles.remove(emptyGroundTile.getGridPosition());
                    interactableTiles.put(cornTile.getGridPosition(), cornTile);
                    cornTile.update((float)0.1);
                    inventory.remove(ItemType.CORN_SEED,1);
                }
                else if (hand.getType() == ItemType.WHEAT_SEED) {
                    System.out.println("posadz pszenice");
                    GrowingWheatTile wheatTile = new GrowingWheatTile(emptyGroundTile);
                    interactableTiles.remove(emptyGroundTile.getGridPosition());
                    interactableTiles.put(wheatTile.getGridPosition(), wheatTile);
                    wheatTile.update((float)0.1);
                    inventory.remove(ItemType.WHEAT_SEED,1);
                }
            }
        }
        System.out.println("nie udalo sie posadzic");
    }

    public void update(float delta) {
        updateGrowingTiles(delta);
        dayNightCycle.update(delta);

        if (dayNightCycle.isNight()) {
            if (!nightEffectApplied) {
                handleNightStart();
                nightEffectApplied = true;
            }
        } else {
            nightEffectApplied = false;
            isTorchThisNight = false;
        }
    }

    private void updateGrowingTiles(float delta) {
        for (InteractableTile tile : interactableTiles.values()) {
            if (tile instanceof GrowingGroundTile) {
                GrowingGroundTile growingTile = (GrowingGroundTile) tile;
                growingTile.update(delta);
            }
        }
    }

    private void handleNightStart() {
        isTorchThisNight = inventory.remove(ItemType.TORCH, 1);

        if (isTorchThisNight) {
            System.out.println("Pochodnia zostala zuzyta na te noc.");
        }

        if (inventory.has(ItemType.SHIELD, 1)) {
            System.out.println("Masz tarcze - uprawy sa bezpieczne.");
            inventory.remove(ItemType.SHIELD, 1);
            return;
        }

        removeRandomCrops();
    }

    private void removeRandomCrops() {
        List<GridPosition> positionsToClear = new ArrayList<>();

        for (Map.Entry<GridPosition, InteractableTile> entry : interactableTiles.entrySet()) {
            InteractableTile tile = entry.getValue();

            if (tile instanceof GrowingGroundTile && random.nextInt(NIGHT_DESTROY_CHANCE) == 0) {
                positionsToClear.add(entry.getKey());
            }
        }

        for (GridPosition position : positionsToClear) {
            InteractableTile oldTile = interactableTiles.get(position);

            if (oldTile instanceof GrowingGroundTile) {
                GrowingGroundTile growingGroundTile = (GrowingGroundTile) oldTile;
                EmptyGroundTile emptyGroundTile = new EmptyGroundTile(growingGroundTile);

                if (oldTile.isSelected()) {
                    emptyGroundTile.select();
                }

                interactableTiles.put(position, emptyGroundTile);
            }
        }

        System.out.println("Noc zniszczyla uprawy: " + positionsToClear.size());
    }

    public boolean isNight() {
        return dayNightCycle.isNight();
    }

    public boolean hasTorch() {
        return isTorchThisNight;
    }

    public boolean isNightWithoutTorch() {
        return isNight() && !hasTorch();
    }

    public float getNightRemainingTime() {
        if (!isNight()) {
            return 0f;
        }

        return dayNightCycle.getRemainingTime();
    }
}
