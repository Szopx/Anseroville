package io.github.anseroville.viewModel;


import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.Player;
import io.github.anseroville.model.shop.ShopManager;
import io.github.anseroville.model.systems.CollectingManager;
import io.github.anseroville.model.tiles.*;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.quest.QuestManager;

import java.util.*;

public class FarmViewModel {
    private static final int TILE_WIDTH = 75;
    private static final int TILE_HEIGHT = 75;
    private static final int PLAYER_REACH = 50;

    private final GameState gameState;
    private InteractableTile selectedTile;
    private final CollectingManager collectingManager;
    private final QuestManager questManager;
    private boolean isInventoryOpen = false;
    private ShopManager shopManager;

    public FarmViewModel(GameState gameState, CollectingManager collectingManager, QuestManager questManager, ShopManager shopManager) {
        this.gameState = gameState;
        this.collectingManager = collectingManager;
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
            GrowingState growingState = null;
            if (tile instanceof GrowingGroundTile){
                plantType = ((GrowingGroundTile) tile).getPlantType();
                growingState = ((GrowingGroundTile) tile).getGrowingState();
            }



            tileViewStates.add(new TileViewState(
            tile.getGridPosition().getX(),tile.getGridPosition().getY()
           ,tile.isSelected(), growingState, plantType));
        }

        return tileViewStates;
    }

    public ShopViewState getShopViewState() {
        if (shopManager.isCurrentShopAvailable()) {
            return new ShopViewState(Collections.unmodifiableMap(shopManager.getCurrentShopBuyPrices()),
                    Collections.unmodifiableMap(shopManager.getCurrentShopSellPrices()));
        }
        else {
            return new ShopViewState(Collections.emptyMap(), Collections.emptyMap());
        }
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
        if(!collectingManager.collect(selectedTile)) {
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

    public QuestViewState getQuestViewState() {
        if (questManager.isActiveQuestAvailable()) {
            return new QuestViewState(true,
                    Collections.unmodifiableMap(questManager.getActiveQuestRequiredItems()));
        }
        else {
            return new QuestViewState(false, Collections.emptyMap());
        }
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
        gameState.getHand().toggleHand(itemType);
    }

    public void playMachine() {
        gameState.getMachine().play();
    }

    public void sellItem(ItemType type) {
        shopManager.sellItem(type);
    }

    public void buyItem(ItemType type) {
        shopManager.buyItem(type);
    }

    //mozna zmienic nazwe, ogolnie to jest tylko dla activity tile
    // docelowo ma byc zamiennikiem playMachine(), completeMainQuest() itp
    // mozna przekopiowac kod a mozna dac tamte jako prywatne
    public boolean interactWithTile() {
        if (!(selectedTile instanceof ActivityTile)) return false;

        ActivityTile activityTile = (ActivityTile) selectedTile;
        ActivityTileType activityTileType = activityTile.getActivityTileType();

        switch (activityTileType)
        {
            case SHOP:
                // do implementacji - albo tutaj wywolujemy prywatna funkcje, albo caly jej kod tu ma byc
                break;
            case MAIN_QUEST:
                completeMainQuest();
                break;
            case QUEST:
                completeActiveQuest();
                break;
            case GAMBLING:
                gameState.getMachine().play();
                break;
            case WATER:
                // jeszcze nie ma napelniania konewki
                break;
        }

        updateSelectedTile();
        return true;
    }

}