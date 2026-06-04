package io.github.anseroville;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.anseroville.model.systems.*;
import io.github.anseroville.model.GameState;
import io.github.anseroville.model.shop.ShopManager;
import io.github.anseroville.model.quest.QuestManager;
import io.github.anseroville.model.tiles.TileData;
import io.github.anseroville.model.settings.GameSettings;
import io.github.anseroville.view.*;
import io.github.anseroville.viewModel.FarmViewModel;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;

import java.util.Random;

public class AnserovilleGame extends ApplicationAdapter {
    private FarmViewModel farmViewModel;
    private FarmRenderer farmRenderer;
    private HandRenderer handRenderer;
    private QuestRenderer questRenderer;
    private InventoryRenderer inventoryRenderer;
    private StatusBarRenderer statusBarRenderer;
    private NightRenderer nightRenderer;
    private FarmInputController farmInputController;
    private AssetProvider assetProvider;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private SettingsRenderer settingsRenderer;
    private HelpRenderer helpRenderer;

    private static final float width = 1920f;
    private static final float height = 1080f;

    @Override
    public void create() {
        GameState gameState = new GameState();

        GameSettings gameSettings = new GameSettings();

        NightManager nightManager = new NightManager(gameState.getInventory(), gameState.getWorldState());
        CropGrowthSystem cropGrowthSystem = new CropGrowthSystem(gameState.getWorldState());
        CollectingManager collectingManager = new CollectingManager(gameState.getInventory());
        PlantingManager plantingManager = new PlantingManager(
                gameState.getHand(),
                gameState.getInventory()
        );

        ShopManager shopManager = new ShopManager(
                gameState.getWallet(),
                gameState.getInventory()
        );

        QuestManager questManager = new QuestManager(
                gameState,
                shopManager
        );

        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);
        assetProvider=new AssetProvider();
        batch=new SpriteBatch();
        shapeRenderer=new ShapeRenderer();
        farmViewModel = new FarmViewModel(gameState, cropGrowthSystem, plantingManager,
                nightManager, collectingManager, questManager, shopManager,  gameSettings);
        farmRenderer = new FarmRenderer(farmViewModel, camera, assetProvider, batch, shapeRenderer);
        handRenderer = new HandRenderer(camera, batch, shapeRenderer, assetProvider);
        inventoryRenderer = new InventoryRenderer(camera, assetProvider, batch);
        questRenderer = new QuestRenderer(farmViewModel, camera, assetProvider, batch);
        farmInputController = new FarmInputController(farmViewModel, viewport);
        statusBarRenderer = new StatusBarRenderer(farmViewModel, camera, assetProvider, batch);
        nightRenderer = new NightRenderer(farmViewModel, camera, assetProvider, batch);
        settingsRenderer = new SettingsRenderer(farmViewModel, camera, batch, shapeRenderer, assetProvider);
        helpRenderer = new HelpRenderer(farmViewModel, camera, batch, shapeRenderer, assetProvider);
    }

    @Override
    public void render() {
        camera.update();
        float delta = Gdx.graphics.getDeltaTime();

        farmInputController.handleInput();
        farmViewModel.update(delta);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        farmRenderer.render();
        if (farmViewModel.isQuestPanelVisible()) {
            questRenderer.render();
        }
        if(farmViewModel.isInventoryOpen()){
            inventoryRenderer.render(farmViewModel.getInventoryViewState());
        }
        if(farmViewModel.isNightWithoutTorch()){
            farmRenderer.render();
        } //nieoptymalne, chodzi o to że musi być wakaźnik czy jest noc w trakcie otwatrego inventory albo żeby inventory nie liczyło czasu bo noc odbiera ruszanie sie todo do poprawy
        statusBarRenderer.render(); //zakladam ze to moze byc caly czas widoczne, correct me if i'm wrong
        handRenderer.render(farmViewModel.getInventoryViewState());
        nightRenderer.render();

        settingsRenderer.render();
        helpRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        assetProvider.dispose();
    }
}