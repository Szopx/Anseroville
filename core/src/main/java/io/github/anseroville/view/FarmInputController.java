package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FarmInputController {
    private final FarmViewModel viewModel;
    private final Viewport viewport;

    public FarmInputController(FarmViewModel viewModel, Viewport viewport) {
        this.viewModel = viewModel;
        this.viewport=viewport;
    }

    public void handleInput() {
        if(viewModel.isNightWithoutTorch()) {
            return;
        }

        if (viewModel.isSettingsOpen()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                viewModel.toggleSettings();
                return;
            } else return;
        }

        if(viewModel.isHelpOpen()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                viewModel.toggleHelp();
                return;
            } else return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            viewModel.toggleSettings();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            viewModel.toggleHelp();
            return;
        }

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            viewModel.toggleInventory();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            viewModel.toggleShop();
        }

        if (viewModel.isInventoryOpen()) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                viewport.unproject(clickPosition);
                handleInventoryClick(clickPosition.x, clickPosition.y);
            }
            return;
        }

        if (viewModel.isShopOpen()) {
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                viewport.unproject(clickPosition);
                handleShopClick(clickPosition.x, clickPosition.y);
            }
            return;
        }

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

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            viewModel.teleportPlayer();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            viewModel.plant();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            viewModel.collect();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            viewModel.completeActiveQuest(); //todo: trzeba jeszcze sprawdzic chyba gdzie sie stoi
            // na przyszlosc nie musi tu byc osobny klawisz jak bedzie zalezalo od selected tile
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            viewModel.completeMainQuest(); //todo: trzeba jeszcze sprawdzic chyba gdzie sie stoi
            //tak jak w completeActiveQuest();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) { //todo: oba tymczasowo
            viewModel.addItemToInventory(ItemType.TORCH, 1);
            System.out.println("Dodano pochodnie");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {// todo: oba tymczasowo
            viewModel.addItemToInventory(ItemType.SHIELD, 1);
            System.out.println("Dodano tarcze");
        }

    }
    private void handleInventoryClick(float clickX, float clickY) {

        float startX = viewport.getWorldWidth() * 0.12f;
        float startY = viewport.getWorldHeight() * 0.73f;
        float offsetX = 170f;
        float offsetY = 180f;
        int columns = 4;
        int ICON_SIZE = 90;

        int index = 0;
        for (ItemType itemType : ItemType.values()) {
                int col = index % columns;
                int row = index / columns;
                float drawX = startX + (col * offsetX);
                float drawY = startY - (row * offsetY);
                if (clickX >= drawX && clickX <= drawX + ICON_SIZE &&
                        clickY >= drawY && clickY <= drawY + ICON_SIZE) {
                    viewModel.selectItemFromInventory(itemType);
                    break;
                }
                index++;
        }
    }
    private void handleShopClick(float clickX, float clickY) {

        float startX = viewport.getWorldWidth() * 0.1f;
        float startY = viewport.getWorldHeight() * 0.545f;
        float offsetX = 127f;
        float offsetY = 140f;
        int columns = 5;
        int ICON_SIZE = 85;

        int index = 0;
        for (ItemType itemType : viewModel.getShopViewState().getBuyPrices().keySet()) {
            int col = index % columns;
            int row = index / columns;
            float drawX = startX + (col * offsetX);
            float drawY = startY - (row * offsetY);
            if (clickX >= drawX && clickX <= drawX + ICON_SIZE &&
                    clickY >= drawY && clickY <= drawY + ICON_SIZE) {
                viewModel.buyItem(itemType);
                break;
            }
            index++;
        }
        startX = viewport.getWorldWidth() * 0.59f;
        index = 0;
        offsetX = 135f;
        for (ItemType itemType : viewModel.getShopViewState().getSellPrices().keySet()) {
            int col = index % columns;
            int row = index / columns;
            float drawX = startX + (col * offsetX);
            float drawY = startY - (row * offsetY);
            if (clickX >= drawX && clickX <= drawX + ICON_SIZE &&
                    clickY >= drawY && clickY <= drawY + ICON_SIZE) {
                viewModel.sellItem(itemType);
                break;
            }
            index++;
        }
    }
}