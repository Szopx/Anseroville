package io.github.anseroville;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.anseroville.model.GameState;
import io.github.anseroville.view.FarmInputController;
import io.github.anseroville.view.FarmRenderer;
import io.github.anseroville.viewModel.FarmViewModel;

public class AnserovilleGame extends ApplicationAdapter {
    private FarmViewModel farmViewModel;
    private FarmRenderer farmRenderer;
    private FarmInputController farmInputController;

    @Override
    public void create() {
        GameState gameState = new GameState();

        farmViewModel = new FarmViewModel(gameState);
        farmRenderer = new FarmRenderer(farmViewModel);
        farmInputController = new FarmInputController(farmViewModel);
    }

    @Override
    public void render() {
        farmInputController.handleInput();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        farmRenderer.render();
    }

    @Override
    public void dispose() {
        farmRenderer.dispose();
    }
}