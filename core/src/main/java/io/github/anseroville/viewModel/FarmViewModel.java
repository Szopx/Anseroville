package io.github.anseroville.viewModel;


import io.github.anseroville.enums.Direction;
import io.github.anseroville.model.*;
import io.github.anseroville.model.Shop.Shop;
import io.github.anseroville.model.Shop.ShopManager;
import io.github.anseroville.model.Tiles.*;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.quest.Quest;
import io.github.anseroville.model.quest.QuestManager;

import java.util.*;

public class FarmViewModel {
    private static final int TILE_WIDTH = 75;
    private static final int TILE_HEIGHT = 75;
    private static final int PLAYER_REACH = 50;

    private final GameState gameState;
    private InteractableTile selectedTile;
    private final Collector collector;
    private final QuestManager questManager;
    private boolean isInventoryOpen = false;
    private ShopManager shopManager;

    public FarmViewModel(GameState gameState, Collector collector, QuestManager questManager, ShopManager shopManager) {
        this.gameState = gameState;
        this.collector = collector;
        this.questManager = questManager;
        this.shopManager = shopManager;
    }

    public void movePlayer(Direction direction) {
        gameState.getPlayer().move(direction);
        updateSelectedTile();
    }

    public void teleportPlayer() {
        gameState.getPlayer().teleportToStart();
        updateSelectedTile();
    }

    public void plant() {
        gameState.plant(selectedTile);
        updateSelectedTile();
    }

    public void water() {
        gameState.water(selectedTile);
    }

    private void updateSelectedTile() {
        if (selectedTile != null) {
            selectedTile.unselect();
        }

        GridPosition lookedPosition = getLookedGridPosition();

        selectedTile = gameState.getInteractableTiles().get(lookedPosition);

        if (selectedTile != null) {
            selectedTile.select();
        }
    }

    private GridPosition getLookedGridPosition() {
        Player player = gameState.getPlayer();

        int lookedX = player.getX();
        int lookedY = player.getY();

        switch (player.getDirection()) {
            case UP -> lookedY += PLAYER_REACH;
            case DOWN -> lookedY -= PLAYER_REACH;
            case LEFT -> lookedX -= PLAYER_REACH;
            case RIGHT -> lookedX += PLAYER_REACH;
        }

        return new GridPosition(lookedX / TILE_WIDTH, lookedY / TILE_HEIGHT);
    }

    public PlayerViewState getPlayerViewState() {
        Player player = gameState.getPlayer();

        return new PlayerViewState(
                player.getX(),
                player.getY(),
                player.getDirection()
        );
    }

    public List<TileViewState> getTileViewStates() {
        List<TileViewState> tileViewStates = new ArrayList<>();

        for (Map.Entry<GridPosition, InteractableTile> entry : gameState.getInteractableTiles().entrySet()) {
            InteractableTile tile = entry.getValue();

            ItemType plantType = null;
            if (tile instanceof GrowingGroundTile){
                plantType = ((GrowingGroundTile) tile).getPlantType();
            }



            tileViewStates.add(new TileViewState(
            tile.getGridPosition().getX(),tile.getGridPosition().getY()
           ,tile.isSelected(), tile.getGrowingState(), plantType));
        }

        return tileViewStates;
    }

    public ShopViewState getShopViewState() {
        Shop shop = shopManager.getCurrentShop();
        return new ShopViewState(shop.getBuyPrices(), shop.getSellPrices());
    }

    public InventoryViewState getInventoryViewState(){
        Inventory inventory = gameState.getInventory();
        Hand hand = gameState.getHand();

        Map<ItemType, Integer> stateMap = new EnumMap<>(ItemType.class);

        for (ItemType type : ItemType.values()) {
            stateMap.put(type, inventory.getAmount(type));
        }

        return new InventoryViewState(stateMap,hand.getType(),hand.getAmount());
    }

    public void update(float delta) {
        gameState.update(delta);
    }

    public void collect() {
        if(!collector.collect(selectedTile)) {
            System.out.println("nie udało się zebrać warzywa");
        } else {
            System.out.println("udało się zebrać warzywa");
        }
    }

    public void completeActiveQuest() {
        boolean completed = questManager.completeActiveQuest();

        if (!completed) {
            System.out.println("nie udało się zrobic questa");
        } else {
            System.out.println("udało się zrobic questa");
        }
    }

    public void completeMainQuest() {
        boolean completed = questManager.completeMainQuest();

        if (!completed) {
            System.out.println("nie udało się zrobic main questa");
        } else {
            System.out.println("udało się zrobic main questa");
        }
    }

    public int getMoney() {
        return gameState.getWallet().getMoney();
    }

    public Quest getActiveQuest() {
        return questManager.getActiveQuest();
    }

    public QuestViewState getQuestViewState() {
        Quest activeQuest = questManager.getActiveQuest();
        if (activeQuest==null) {
            return new QuestViewState(false, Collections.emptyMap());
        } else {
            return new QuestViewState(true, Collections.unmodifiableMap(activeQuest.getRequiredItems()));
        }
    }

    public boolean isActiveQuestAvailable() {
        return getActiveQuest() == null;
    }

    public void toggleInventory() {

        isInventoryOpen = !isInventoryOpen;
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public NightViewState getNightViewState() {
        return new NightViewState(gameState.isNight(), gameState.hasTorch(), gameState.getNightRemainingTime());
    }

    public boolean isNightWithoutTorch() {
        return gameState.isNightWithoutTorch();
    }

    public void addItemToInventory(ItemType itemType, int amount) {
        gameState.getInventory().add(itemType, amount);
    }

    public void selectItemFromInventory(ItemType itemType) {
        gameState.toggleHand(itemType);
    }

    public void playMachine() {
        gameState.getMachine().play();
    }

}