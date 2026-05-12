package io.github.anseroville;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.inventory.ItemType;
import io.github.anseroville.view.FarmInputController;
import io.github.anseroville.view.FarmRenderer;
import io.github.anseroville.view.HandRenderer;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.InventoryViewState;
import java.util.HashMap;

public class AnserovilleGame extends ApplicationAdapter {
    private FarmViewModel farmViewModel;
    private InventoryViewState inventoryViewState;
    private FarmRenderer farmRenderer;
    private HandRenderer handRenderer;
    private FarmInputController farmInputController;

    @Override
    public void create() {
        GameState gameState = new GameState();

        farmViewModel = new FarmViewModel(gameState);
        inventoryViewState=new InventoryViewState(new HashMap<ItemType, Integer>(), ItemType.CARROT, 1);
        farmRenderer = new FarmRenderer(farmViewModel);
        handRenderer = new HandRenderer(inventoryViewState);
        farmInputController = new FarmInputController(farmViewModel);
    }

    @Override
    public void render() {
        farmInputController.handleInput();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        farmRenderer.render();
        handRenderer.render();
    }

    @Override
    public void dispose() {
        farmRenderer.dispose();
        handRenderer.dispose();
    }
}