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
    private FarmRenderer farmRenderer;
    private HandRenderer handRenderer;
    private QuestRenderer questRenderer;
    private InventoryRenderer inventoryRenderer;
    private StatusBarRenderer statusBarRenderer;
    private FarmInputController farmInputController;

    @Override
    public void create() {
        GameState gameState = new GameState();
        Collector collector = new Collector(gameState);
        Wallet wallet = new Wallet();
        QuestManager questManager = new QuestManager(wallet, gameState.getInventory());
        ShopManager shopManager = new ShopManager(wallet, gameState.getInventory());

        farmViewModel = new FarmViewModel(gameState, collector, questManager, shopManager);
        farmRenderer = new FarmRenderer(farmViewModel);
        handRenderer = new HandRenderer();
        inventoryRenderer = new InventoryRenderer();
        questRenderer = new QuestRenderer(farmViewModel);
        farmInputController = new FarmInputController(farmViewModel);
        statusBarRenderer = new StatusBarRenderer(farmViewModel);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        farmInputController.handleInput();
        farmViewModel.update(delta);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        farmRenderer.render();
        questRenderer.render();
        if(farmViewModel.isInventoryOpen()){
            inventoryRenderer.render(farmViewModel.getInventoryViewState());
        }
        if(farmViewModel.isNightWithoutTorch()){
            farmRenderer.render();
        } //nieoptymalne, chodzi o to że musi być wakaźnik czy jest noc w trakcie otwatrego inventory albo żeby inventory nie liczyło czasu bo noc odbiera ruszanie sie todo do poprawy
        statusBarRenderer.render(); //zakladam ze to moze byc caly czas widoczne, correct me if i'm wrong
        handRenderer.render(farmViewModel.getInventoryViewState(), farmViewModel.isInventoryOpen());
    }

    @Override
    public void dispose() {
        farmRenderer.dispose();
        statusBarRenderer.dispose();
        inventoryRenderer.dispose();
        questRenderer.dispose();
        handRenderer.dispose();
    }
}