package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.anseroville.model.Direction;
import io.github.anseroville.viewModel.FarmViewModel;

public class FarmInputController {
    private final FarmViewModel viewModel;

    public FarmInputController(FarmViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            viewModel.toggleInventory();
        }

        if (viewModel.isInventoryOpen()) {
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
    }
}