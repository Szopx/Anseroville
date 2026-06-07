package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;

public class FarmInputController {
    private static final float SETTINGS_PANEL_WIDTH = 700f;
    private static final float SETTINGS_PANEL_HEIGHT = 610f;

    private final FarmViewModel viewModel;
    private final Viewport viewport;

    public FarmInputController(FarmViewModel viewModel, Viewport viewport) {
        this.viewModel = viewModel;
        this.viewport = viewport;
    }

    public void handleInput() {
        if (viewModel.isGameEndOpen()) {
            handleGameEndInput();
            return;
        }

        if (viewModel.isLobbyOpen()) {
            handleLobbyInput();
            return;
        }

        if (handleGlobalInput()) {
            return;
        }

        if (viewModel.isMissionCompleteOpen()) {
            handleMissionCompleteInput();
            return;
        }

        if (viewModel.isHelpOpen()) {
            return;
        }

        if (viewModel.isSettingsOpen()) {
            handleSettingsInput();
            return;
        }

        handleInventoryInput();

        if (viewModel.isInventoryOpen()) {
            return;
        }

        handleShopInput();

        if (viewModel.isShopOpen()) {
            return;
        }

        if (viewModel.isNightWithoutTorch()) {
            return;
        }

        handlePlayerMovement();
        handleGameActions();
    }

    private boolean handleGlobalInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            viewModel.toggleHelp();
            return true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            viewModel.toggleSettings();
            return true;
        }

        return false;
    }

    private void handleSettingsInput() {
        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        Vector3 clickPosition = getClickPosition();
        handleSettingsClick(clickPosition.x, clickPosition.y);
    }

    private void handleInventoryInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            viewModel.toggleInventory();
            return;
        }

        if (!viewModel.isInventoryOpen()) {
            return;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 clickPosition = getClickPosition();
            handleInventoryClick(clickPosition.x, clickPosition.y);
        }
    }

    private void handleShopInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            viewModel.toggleShop();
            return;
        }

        if (!viewModel.isShopOpen()) {
            return;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 clickPosition = getClickPosition();
            handleShopClick(clickPosition.x, clickPosition.y);
        }
    }

    private void handlePlayerMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            viewModel.movePlayer(Direction.UP);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            viewModel.movePlayer(Direction.DOWN);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            viewModel.movePlayer(Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            viewModel.movePlayer(Direction.RIGHT);
        }
    }

    private void handleGameActions() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            viewModel.addItemToInventory(ItemType.CARROT, 10);
            viewModel.addItemToInventory(ItemType.WHEAT, 10);
            viewModel.addItemToInventory(ItemType.POTATO, 10);
            viewModel.addItemToInventory(ItemType.CORN, 10);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            viewModel.playMachine();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            viewModel.water();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            viewModel.teleportPlayer();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            viewModel.plant();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            viewModel.collect();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            viewModel.completeActiveQuest();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            viewModel.completeMainQuest();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            viewModel.addItemToInventory(ItemType.TORCH, 1);
            System.out.println("Dodano pochodnie");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            viewModel.addItemToInventory(ItemType.SHIELD, 1);
            System.out.println("Dodano tarcze");
        }
    }

    private Vector3 getClickPosition() {
        Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(clickPosition);
        return clickPosition;
    }

    private void handleSettingsClick(float clickX, float clickY) {
        float panelX = (viewport.getWorldWidth() - SETTINGS_PANEL_WIDTH) / 2f;
        float panelY = (viewport.getWorldHeight() - SETTINGS_PANEL_HEIGHT) / 2f;

        if (isInside(clickX, clickY, panelX + 430f, panelY + 425f, 190f, 48f)) {
            viewModel.cycleMovementSpeed();
            return;
        }

        if (isInside(clickX, clickY, panelX + 430f, panelY + 355f, 190f, 48f)) {
            viewModel.cyclePlantGrowthSpeed();
            return;
        }

        if (isInside(clickX, clickY, panelX + 430f, panelY + 285f, 190f, 48f)) {
            viewModel.toggleNightCycle();
            return;
        }

        if (isInside(clickX, clickY, panelX + 430f, panelY + 215f, 190f, 48f)) {
            viewModel.toggleQuestPanelVisible();
            return;
        }

        if (isInside(clickX, clickY, panelX + 430f, panelY + 145f, 190f, 48f)) {
            viewModel.toggleShowFps();
            return;
        }

        if (isInside(clickX, clickY, panelX + 250f, panelY + 55f, 200f, 52f)) {
            viewModel.resetSettings();
        }
    }

    private void handleInventoryClick(float clickX, float clickY) {
        float startX = viewport.getWorldWidth() * 0.12f;
        float startY = viewport.getWorldHeight() * 0.73f;
        float offsetX = 170f;
        float offsetY = 180f;
        int columns = 4;
        int iconSize = 90;

        int index = 0;

        for (ItemType itemType : ItemType.values()) {
            int col = index % columns;
            int row = index / columns;

            float drawX = startX + (col * offsetX);
            float drawY = startY - (row * offsetY);

            if (isInside(clickX, clickY, drawX, drawY, iconSize, iconSize)) {
                viewModel.selectItemFromInventory(itemType);
                break;
            }

            index++;
        }
    }

    private void handleMissionCompleteInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            viewModel.continueAfterMissionComplete();
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        Vector3 clickPosition = getClickPosition();

        float panelX = (viewport.getWorldWidth() - MissionCompleteRenderer.PANEL_WIDTH) / 2f;
        float panelY = (viewport.getWorldHeight() - MissionCompleteRenderer.PANEL_HEIGHT) / 2f;

        float buttonX = panelX + (MissionCompleteRenderer.PANEL_WIDTH - MissionCompleteRenderer.CONTINUE_BUTTON_WIDTH) / 2f;
        float buttonY = panelY + MissionCompleteRenderer.CONTINUE_BUTTON_Y_OFFSET;

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                buttonX,
                buttonY,
                MissionCompleteRenderer.CONTINUE_BUTTON_WIDTH,
                MissionCompleteRenderer.CONTINUE_BUTTON_HEIGHT
        )) {
            viewModel.continueAfterMissionComplete();
        }
    }

    private void handleShopClick(float clickX, float clickY) {
        float startX = viewport.getWorldWidth() * 0.1f;
        float startY = viewport.getWorldHeight() * 0.545f;
        float offsetX = 127f;
        float offsetY = 140f;
        int columns = 5;
        int iconSize = 85;

        int index = 0;

        for (ItemType itemType : viewModel.getShopViewState().getBuyPrices().keySet()) {
            int col = index % columns;
            int row = index / columns;

            float drawX = startX + (col * offsetX);
            float drawY = startY - (row * offsetY);

            if (isInside(clickX, clickY, drawX, drawY, iconSize, iconSize)) {
                viewModel.buyItem(itemType);
                break;
            }

            index++;
        }

        startX = viewport.getWorldWidth() * 0.59f;
        offsetX = 135f;
        index = 0;

        for (ItemType itemType : viewModel.getShopViewState().getSellPrices().keySet()) {
            int col = index % columns;
            int row = index / columns;

            float drawX = startX + (col * offsetX);
            float drawY = startY - (row * offsetY);

            if (isInside(clickX, clickY, drawX, drawY, iconSize, iconSize)) {
                viewModel.sellItem(itemType);
                break;
            }

            index++;
        }
    }

    private void handleGameEndInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            viewModel.backToLobbyAfterGameEnd();
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        Vector3 clickPosition = getClickPosition();

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                GameEndRenderer.getButtonX(viewport.getWorldWidth()),
                GameEndRenderer.getButtonY(viewport.getWorldHeight()),
                GameEndRenderer.BUTTON_WIDTH,
                GameEndRenderer.BUTTON_HEIGHT
        )) {
            viewModel.backToLobbyAfterGameEnd();
        }
    }

    private void handleLobbyInput() {
        if (viewModel.isHelpOpen()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                viewModel.toggleHelp();
            }

            return;
        }

        if (viewModel.isSettingsOpen()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                viewModel.toggleSettings();
                return;
            }

            handleSettingsInput();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            viewModel.startGame();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            viewModel.toggleHelp();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            viewModel.toggleSettings();
            return;
        }

        if (!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return;
        }

        Vector3 clickPosition = getClickPosition();

        float buttonX = LobbyRenderer.getButtonX(viewport.getWorldWidth());

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                buttonX,
                LobbyRenderer.getStartButtonY(viewport.getWorldHeight()),
                LobbyRenderer.BUTTON_WIDTH,
                LobbyRenderer.BUTTON_HEIGHT
        )) {
            viewModel.startGame();
            return;
        }

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                buttonX,
                LobbyRenderer.getHelpButtonY(viewport.getWorldHeight()),
                LobbyRenderer.BUTTON_WIDTH,
                LobbyRenderer.BUTTON_HEIGHT
        )) {
            viewModel.toggleHelp();
            return;
        }

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                buttonX,
                LobbyRenderer.getSettingsButtonY(viewport.getWorldHeight()),
                LobbyRenderer.BUTTON_WIDTH,
                LobbyRenderer.BUTTON_HEIGHT
        )) {
            viewModel.toggleSettings();
            return;
        }

        if (isInside(
                clickPosition.x,
                clickPosition.y,
                buttonX,
                LobbyRenderer.getExitButtonY(viewport.getWorldHeight()),
                LobbyRenderer.BUTTON_WIDTH,
                LobbyRenderer.BUTTON_HEIGHT
        )) {
            Gdx.app.exit();
        }
    }

    private boolean isInside(
            float clickX,
            float clickY,
            float x,
            float y,
            float width,
            float height
    ) {
        return clickX >= x
                && clickX <= x + width
                && clickY >= y
                && clickY <= y + height;
    }
}