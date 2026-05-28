package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;

public class FarmInputController {
    private final FarmViewModel viewModel;

    public FarmInputController(FarmViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void handleInput() {
        if (viewModel.isNightWithoutTorch()) {
            return;
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

        if (viewModel.isInventoryOpen()) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                handleInventoryClick(Gdx.input.getX(), Gdx.input.getY());
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
    private void handleInventoryClick(int screenX, int screenY) {
        int renderY = Gdx.graphics.getHeight() - screenY;

        float startX = Gdx.graphics.getWidth() * 0.12f;
        float startY = Gdx.graphics.getHeight() * 0.73f;
        float offsetX = 160f;
        float offsetY = 165f;
        int columns = 4;
        int ICON_SIZE = 100;

        int index = 0;
        for (ItemType itemType : ItemType.values()) {
                int col = index % columns;
                int row = index / columns;
                float drawX = startX + (col * offsetX);
                float drawY = startY - (row * offsetY);
                if (screenX >= drawX && screenX <= drawX + ICON_SIZE &&
                        renderY >= drawY && renderY <= drawY + ICON_SIZE) {
                    viewModel.selectItemFromInventory(itemType);
                    break;
                }
                index++;
        }
    }
}