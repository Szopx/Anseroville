package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;

public class HelpRenderer {
    private static final float PANEL_WIDTH = 780f;
    private static final float PANEL_HEIGHT = 560f;

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont titleFont;
    private final BitmapFont font;

    public HelpRenderer(
            FarmViewModel viewModel,
            OrthographicCamera camera,
            SpriteBatch batch,
            ShapeRenderer shapeRenderer,
            AssetProvider assetProvider
    ) {
        this.viewModel = viewModel;
        this.camera = camera;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.titleFont = assetProvider.getBigFont();
        this.font = assetProvider.getMediumFont();
    }

    public void render() {
        if (!viewModel.isHelpOpen()) {
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        renderBackground();
        renderText();
    }

    private void renderBackground() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0f, 0f, 0f, 0.65f));
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        shapeRenderer.setColor(new Color(0.05f, 0.08f, 0.06f, 1f));
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(new Color(0.12f, 0.19f, 0.12f, 1f));
        shapeRenderer.rect(panelX + 18f, panelY + 18f, PANEL_WIDTH - 36f, PANEL_HEIGHT - 36f);

        shapeRenderer.setColor(new Color(0.86f, 0.64f, 0.25f, 1f));
        shapeRenderer.rect(panelX, panelY + PANEL_HEIGHT - 10f, PANEL_WIDTH, 10f);

        shapeRenderer.setColor(new Color(0.28f, 0.45f, 0.22f, 1f));
        shapeRenderer.rect(panelX + 40f, panelY + 400f, PANEL_WIDTH - 80f, 52f);
        shapeRenderer.rect(panelX + 40f, panelY + 255f, PANEL_WIDTH - 80f, 52f);
        shapeRenderer.rect(panelX + 40f, panelY + 115f, PANEL_WIDTH - 80f, 52f);

        shapeRenderer.end();
    }

    private void renderText() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        batch.begin();

        titleFont.draw(batch, "HOW TO PLAY", panelX + 275f, panelY + PANEL_HEIGHT - 55f);

        font.draw(batch, "Your goal is to grow crops, complete quests and unlock new levels.", panelX + 70f, panelY + 470f);
        font.draw(batch, "Each level has side quests and one main quest. Complete the main quest to advance.", panelX + 70f, panelY + 440f);

        font.draw(batch, "MOVEMENT & FARMING", panelX + 65f, panelY + 435f - 65f);
        font.draw(batch, "Move around the farm using your movement keys.", panelX + 80f, panelY + 335f);
        font.draw(batch, "H  - plant selected seed on an empty field", panelX + 80f, panelY + 305f);
        font.draw(batch, "W  - water a planted field so the crop can grow", panelX + 80f, panelY + 275f);
        font.draw(batch, "C  - collect a fully grown crop", panelX + 80f, panelY + 245f);

        font.draw(batch, "INVENTORY, SHOP & QUESTS", panelX + 65f, panelY + 435f - 210f);
        font.draw(batch, "I  - open or close inventory", panelX + 80f, panelY + 190f);
        font.draw(batch, "E  - complete current side quest if you have required items", panelX + 80f, panelY + 160f);
        font.draw(batch, "M  - complete main quest and move to the next level", panelX + 80f, panelY + 130f);

        font.draw(batch, "OTHER", panelX + 65f, panelY + 435f - 350f);
        font.draw(batch, "O      - open settings", panelX + 80f, panelY + 50f + 55f);
        font.draw(batch, "ESC    - close this help screen", panelX + 80f, panelY + 50f + 25f);

        font.draw(batch, "Tip: crops are visible after planting, but they grow only after watering.", panelX + 150f, panelY + 35f);

        batch.end();
    }

    private float getPanelX() {
        return (camera.viewportWidth - PANEL_WIDTH) / 2f;
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_HEIGHT) / 2f;
    }
}