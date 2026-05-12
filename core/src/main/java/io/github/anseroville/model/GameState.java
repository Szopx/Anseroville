package io.github.anseroville.model;

import io.github.anseroville.model.Tiles.GroundTile;
import io.github.anseroville.model.Tiles.GrowingGroundTile;
import io.github.anseroville.model.Tiles.InteractableTile;
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
        this.hand = new Hand();
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
            tiles.put(gridPosition, new GrowingGroundTile(gridPosition));
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

    //na razie zakladam, ze trzymamy dany typ przedmiotu albo w inventory, albo w hand
    //jak przenosimy z jednego miejsca do drugiego to przenosimy calosc
    //jesli trzymamy juz dany typ itemu w hand i go podniesiemy to dodajemy do hand
    //w przeciwnym wypadku niezaleznie od tego czy hand jest zajety dajemy item do inventory

    public void toggleHand(ItemType clickedType) {
        if (!hand.isEmpty()) {
            ItemType typeInHand = hand.getType();
            inventory.add(typeInHand, hand.getAmount());
            hand.clear();

            if (typeInHand == clickedType) return;
        }

        int amountInInventory = inventory.getAmount(clickedType);
        if (amountInInventory > 0) {
            inventory.remove(clickedType, amountInInventory);
            hand.set(clickedType, amountInInventory);
        }
    }

    //todo
    // zlaczyc tą koncepcje z collectorem
    public boolean pickUpItem(ItemType type, int amount) {
        int inHand = hand.getAmountOf(type);
        int inInventory = inventory.getAmount(type);

        if (inHand + inInventory + amount > 99) {
            return false;
        }

        if (inHand > 0) {
            hand.set(type, inHand + amount);
            return true;
        } else {
            return inventory.add(type, amount);
        }
    }
}