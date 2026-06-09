package io.github.anseroville.viewModel;


import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.Player;
import io.github.anseroville.model.levels.LevelManager;
import io.github.anseroville.model.shop.ShopManager;
import io.github.anseroville.model.systems.*;
import io.github.anseroville.model.tiles.*;
import io.github.anseroville.model.inventory.Hand;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.quest.QuestManager;
import io.github.anseroville.model.settings.GameSettings;

import java.util.*;

public class FarmViewModel {
    private static final int TILE_WIDTH = 75;
    private static final int TILE_HEIGHT = 75;
    private static final int PLAYER_REACH = 50;

    private final GameState gameState;
    private InteractableTile selectedTile;
    private final CropGrowthSystem cropGrowthSystem;
    private final NightManager nightManager;
    private final PlantingManager plantingManager;
    private final CollectingManager collectingManager;
    private final QuestManager questManager;
    private final LevelManager levelManager;
    private final ShopManager shopManager;
    private final CollisionManager collisionManager;
    private boolean isInventoryOpen = false;
    private boolean isHelpOpen = false;
    private boolean isShopOpen = false;
    private boolean isGameEndOpen = false;

    private boolean isLobbyOpen = true;

    private boolean missionCompleteOpen = false;
    private int completedLevelNumber = 0;

    private final GameSettings gameSettings;
    private boolean isSettingsOpen = false;

    public FarmViewModel(
            GameState gameState,
            CropGrowthSystem cropGrowthSystem,
            PlantingManager plantingManager,
            NightManager nightManager,
            CollectingManager collectingManager,
            QuestManager questManager,
            LevelManager levelManager,
            ShopManager shopManager,
            CollisionManager collisionManager,
            GameSettings gameSettings
    ) {
        this.gameState = gameState;
        this.cropGrowthSystem = cropGrowthSystem;
        this.nightManager = nightManager;
        this.plantingManager = plantingManager;
        this.collectingManager = collectingManager;
        this.questManager = questManager;
        this.levelManager = levelManager;
        this.shopManager = shopManager;
        this.collisionManager = collisionManager;
        this.gameSettings = gameSettings;

        this.levelManager.initializeCurrentLevel();
    }

    public void movePlayer(Direction direction) {
        Player player = gameState.getPlayer();

        int targetX = player.getX();
        int targetY = player.getY();

        int speed = player.getSpeed();

        switch (direction) {
            case UP -> targetY += speed;
            case DOWN -> targetY -= speed;
            case LEFT -> targetX -= speed;
            case RIGHT -> targetX += speed;
        }
        if (collisionManager.canMoveTo(targetX, targetY)) {
            player.setPosition(targetX, targetY);
            player.setDirection(direction);
        } else {
            player.setDirection(direction);
        }

        updateSelectedTile();
    }

    public void teleportPlayer() {
        gameState.getPlayer().teleportToStart();
        updateSelectedTile();
    }

    public void plant() {
        if (plantingManager.plant(selectedTile)) {
            System.out.println("posadzono rosline");
        }
        else {
            System.out.println("nie udalo sie posadzic");
        }
        updateSelectedTile();
    }

    public void water() {
        if (gameState.getHand().getType() == ItemType.WATERING_CAN && plantingManager.water(selectedTile)) {
            System.out.println("udało się podlac");
        }
        else {
            System.out.println("nie udalo sie podlac");
        }
    }

    private void updateSelectedTile() {
        if (selectedTile != null) {
            selectedTile.unselect();
        }

        GridPosition lookedPosition = getLookedGridPosition();

        selectedTile = gameState.getWorldState().getTile(lookedPosition);

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

        for (Map.Entry<GridPosition, InteractableTile> entry : gameState.getWorldState().getTilesView().entrySet()) {
            InteractableTile tile = entry.getValue();

            ItemType plantType = null;
            GrowingState growingState = null;
            boolean watered = false;
            ActivityTileType activityType = null;

            if (tile instanceof GroundTile groundTile) {
                watered = groundTile.isWatered();

                if (groundTile.hasCrop()) {
                    Crop crop = groundTile.getCrop();
                    plantType = crop.getHarvestItem();
                    growingState = crop.getGrowingState();
                }
            }
            else if (tile instanceof ActivityTile activityTile) {
                activityType = activityTile.getActivityTileType();
            }

            tileViewStates.add(new TileViewState(
                    tile.getGridPosition().getX(),
                    tile.getGridPosition().getY(),
                    tile.isSelected(),
                    growingState,
                    plantType,
                    watered,
                    activityType
            ));
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

        return new InventoryViewState(stateMap,hand.getType(),hand.getAmount(),
                inventory.getWateringCan().getCurrentWater(),inventory.getWateringCan().getMaxWater());
    }

    public void update(float delta) {
        if (isLobbyOpen || isGameEndOpen || missionCompleteOpen) {
            return;
        }

        float plantGrowthDelta = delta * gameSettings.getPlantGrowthMultiplier();

        cropGrowthSystem.update(plantGrowthDelta);

        if (gameSettings.isNightCycleEnabled()) {
            nightManager.update(delta);
        }
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
        int currentLevelNumber = levelManager.getActiveLevelNumber();

        boolean completed = questManager.completeMainQuest();

        if (!completed) {
            System.out.println("nie udało się zrobic main questa");
            return;
        }

        completedLevelNumber = currentLevelNumber;
        missionCompleteOpen = true;

        System.out.println("ukończono level " + completedLevelNumber);
    }

    public boolean isMissionCompleteOpen() {
        return missionCompleteOpen;
    }

    public MissionCompleteViewState getMissionCompleteViewState() {
        return new MissionCompleteViewState(
                missionCompleteOpen,
                completedLevelNumber,
                levelManager.getMaxLevelNumber()
        );
    }

    private void closeGameplayOverlays() {
        isHelpOpen = false;
        isSettingsOpen = false;
        isInventoryOpen = false;
        isShopOpen = false;
    }

    public void continueAfterMissionComplete() {
        if (!missionCompleteOpen) {
            return;
        }

        missionCompleteOpen = false;

        levelManager.startNextLevel();

        if (levelManager.isGameFinished()) {
            isGameEndOpen = true;
            selectedTile = null;
            closeGameplayOverlays();

            System.out.println("ukończono wszystkie poziomy");
            return;
        }

        questManager.resetSideQuestProgress();

        updateSelectedTile();

        System.out.println("start levelu " + levelManager.getActiveLevelNumber());
    }

    public int getMoney() {
        return gameState.getWallet().getMoney();
    }

    public QuestViewState getQuestViewState() {
        return new QuestViewState(
                questManager.isActiveQuestAvailable(),
                Collections.unmodifiableMap(questManager.getActiveQuestRequiredItems()),
                Collections.unmodifiableMap(questManager.getMainQuestRequiredItems()),
                questManager.getActiveQuestRewardMoney(),
                questManager.getMainQuestRewardMoney(),
                levelManager.getActiveLevelNumber(),
                levelManager.getMaxLevelNumber(),
                questManager.getActiveSideQuestNumber(),
                questManager.getSideQuestsCount(),
                levelManager.isGameFinished()
        );
    }

    public void toggleInventory() {

        isInventoryOpen = !isInventoryOpen;
    }

    public boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public void toggleShop(){
        isShopOpen = !isShopOpen;
    }

    public boolean isShopOpen() {
        return isShopOpen;
    }

    public NightViewState getNightViewState() {
        if (!gameSettings.isNightCycleEnabled())
            return new NightViewState(false, true, 0f);

        return new NightViewState(nightManager.isNight(), nightManager.hasTorch(), nightManager.getNightRemainingTime());
    }

    public boolean isNightWithoutTorch() {
        return gameSettings.isNightCycleEnabled() && nightManager.isNightWithoutTorch();
    }

    public void addItemToInventory(ItemType itemType, int amount) {
        gameState.getInventory().add(itemType, amount);
    }

    public void selectItemFromInventory(ItemType itemType) {
        gameState.getHand().toggleHand(itemType);
    }

    public void sellItem(ItemType type) {
        shopManager.sellItem(type);
    }

    public void buyItem(ItemType type) {
        shopManager.buyItem(type);
    }

    public boolean interactWithTile() {
        if (!(selectedTile instanceof ActivityTile)) return false;

        ActivityTile activityTile = (ActivityTile) selectedTile;
        ActivityTileType activityTileType = activityTile.getActivityTileType();

        switch (activityTileType)
        {
            case SHOP:
                toggleShop();
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
                gameState.getInventory().getWateringCan().refill();
                break;
        }

        updateSelectedTile();
        return true;
    }

    public void toggleSettings() {
        isSettingsOpen = !isSettingsOpen;
    }

    public boolean isSettingsOpen() {
        return isSettingsOpen;
    }

    public void cycleMovementSpeed() {
        gameSettings.cycleMovementSpeed();
        applyPlayerSpeed();
    }

    public void cyclePlantGrowthSpeed() {
        gameSettings.cyclePlantGrowthSpeed();
    }

    public void toggleNightCycle() {
        gameSettings.toggleNightCycle();
    }

    public void toggleQuestPanelVisible() {
        gameSettings.toggleQuestPanelVisible();
    }

    public void toggleShowFps() {
        gameSettings.toggleShowFps();
    }

    public void resetSettings() {
        gameSettings.resetToDefaults();
        applyPlayerSpeed();
    }

    private void applyPlayerSpeed() {
        gameState.getPlayer().setSpeed(gameSettings.getMovementSpeed().getPlayerSpeed());
    }

    public boolean isQuestPanelVisible() {
        return gameSettings.isQuestPanelVisible();
    }

    public SettingsViewState getSettingsViewState() {
        return new SettingsViewState(
                isSettingsOpen,
                gameSettings.getMovementSpeed().getLabel(),
                gameSettings.getPlantGrowthSpeed().getLabel(),
                gameSettings.isNightCycleEnabled(),
                gameSettings.isQuestPanelVisible(),
                gameSettings.isShowFps()
        );
    }

    public void toggleHelp() {
        isHelpOpen = !isHelpOpen;
    }

    public boolean isHelpOpen() {
        return isHelpOpen;
    }

    public boolean isLobbyOpen() {
        return isLobbyOpen;
    }

    public void startGame() {
        isLobbyOpen = false;
        isGameEndOpen = false;
        missionCompleteOpen = false;
        completedLevelNumber = 0;

        closeGameplayOverlays();
        updateSelectedTile();
    }

    public boolean isGameEndOpen() {
        return isGameEndOpen;
    }

    public GameEndViewState getGameEndViewState() {
        return new GameEndViewState(isGameEndOpen);
    }

    public void backToLobbyAfterGameEnd() {
        isGameEndOpen = false;
        missionCompleteOpen = false;
        resetLevelCount();
        isLobbyOpen = true;

        selectedTile = null;
        closeGameplayOverlays();
    }

    public int getLevelNumber() {
        return levelManager.getActiveLevelNumber();
    }

    public void skipLevel() {
        int currentLevelNumber = levelManager.getActiveLevelNumber();
        questManager.skipMainQuest();

        completedLevelNumber = currentLevelNumber;
        missionCompleteOpen = true;
    }

    public void getItemsForMainQuest() {
        questManager.getItemsForMainQuest();
    }

    public void resetLevelCount() {
        completedLevelNumber = 0;
        levelManager.resetActiveLevelIndex();
        levelManager.initializeCurrentLevel();
    }
}