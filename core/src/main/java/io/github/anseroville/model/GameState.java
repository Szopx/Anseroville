package io.github.anseroville.model;

import io.github.anseroville.model.Tiles.*;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.inventory.ItemType;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private static final int TILE_SIZE = 75;

    private final Player player;
    private final Map<GridPosition, InteractableTile> interactableTiles;
    private final Inventory inventory;
    private final Hand hand;

    public GameState() {
        this.player = new Player(100, 100);
        this.interactableTiles = createInteractableTiles();
        this.inventory = new Inventory();
        this.hand = new Hand(inventory);
        //do testow::
        inventory.add(ItemType.CARROT_SEED,64);
        hand.set(ItemType.CARROT_SEED);
    }

    private Map<GridPosition, InteractableTile> createInteractableTiles() {
        int[][] interactablePlaces = {

                {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6},{2, 7},
                {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6},{3, 7},
                {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6},{4, 7},
                {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6},{5, 7}
        };
        //jak coś to trzeba zrobić interactable places do drzewek i transakcji jak coś todo

        Map<GridPosition, InteractableTile> tiles = new HashMap<>();

        for (int[] place : interactablePlaces) {
            int row = place[0];
            int column = place[1];

            GridPosition gridPosition = new GridPosition(column, row);
            tiles.put(gridPosition, new EmptyGroundTile(gridPosition));
        }

        return tiles;
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

    public Hand getHand() { return hand; }

    public void toggleHand(ItemType clickedType) {
        if (!hand.isEmpty()) {
            ItemType typeInHand = hand.getType();
            hand.clear();

            if (typeInHand == clickedType) return;
        }

        int amountInInventory = inventory.getAmount(clickedType);
        if (amountInInventory > 0) {
            hand.set(clickedType);
        }
    }

    public void update(float delta) {
        for (Object tile : interactableTiles.values()) {
            if (tile instanceof GrowingGroundTile growingTile) {
                growingTile.update(delta);
            }
        }
    }

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
}