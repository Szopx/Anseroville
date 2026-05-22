package io.github.anseroville;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.anseroville.model.Collector;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.Shop.ShopManager;
import io.github.anseroville.model.Wallet;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.quest.QuestManager;
import io.github.anseroville.view.*;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.InventoryViewState;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;

public class AnserovilleGame extends ApplicationAdapter {
    private FarmViewModel farmViewModel;
    private InventoryViewState inventoryViewState;
    private FarmRenderer farmRenderer;
    private HandRenderer handRenderer;
    private QuestRenderer questRenderer;
    private InventoryRenderer inventoryRenderer;
    private FarmInputController farmInputController;

    @Override
    public void create() {
        GameState gameState = new GameState();
        Collector collector = new Collector(gameState);
        Wallet wallet = new Wallet();
        QuestManager questManager = new QuestManager(wallet, gameState.getInventory());
        ShopManager shopManager = new ShopManager(wallet, gameState.getInventory());

        farmViewModel = new FarmViewModel(gameState, collector, questManager, wallet, shopManager);
        inventoryViewState=new InventoryViewState(new HashMap<ItemType, Integer>(), ItemType.CARROT, 1);
        farmRenderer = new FarmRenderer(farmViewModel);
        handRenderer = new HandRenderer();
        inventoryRenderer = new InventoryRenderer();
        questRenderer = new QuestRenderer(farmViewModel);
        farmInputController = new FarmInputController(farmViewModel);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        farmInputController.handleInput();
        farmViewModel.update(delta);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        farmRenderer.render();
        handRenderer.render(farmViewModel.getInventoryViewState(), farmViewModel.isInventoryOpen());
        questRenderer.render(farmViewModel.isInventoryOpen());
        if(farmViewModel.isInventoryOpen()){
            inventoryRenderer.render(farmViewModel.getInventoryViewState());
        }
    }

    @Override
    public void dispose() {
        farmRenderer.dispose();
        handRenderer.dispose();
        inventoryRenderer.dispose();
        questRenderer.dispose();
    }
}