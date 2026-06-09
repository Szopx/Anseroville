package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.SettingsViewState;

public class SettingsRenderer {
    private static final float PANEL_WIDTH = 700f;
    private static final float PANEL_HEIGHT = 610f;

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont titleFont;
    private final BitmapFont font;

    public SettingsRenderer(
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
        SettingsViewState state = viewModel.getSettingsViewState();

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        if (!state.isSettingsOpen()) {
            renderFpsIfEnabled(state);
            return;
        }

        renderPanelBackground();
        renderPanelText(state);
        renderFpsIfEnabled(state);
    }

    private void renderPanelBackground() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.04f, 0.05f, 0.08f, 1f));
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(new Color(0.12f, 0.14f, 0.20f, 1f));
        shapeRenderer.rect(panelX + 18f, panelY + 18f, PANEL_WIDTH - 36f, PANEL_HEIGHT - 36f);

        shapeRenderer.setColor(new Color(0.95f, 0.74f, 0.32f, 1f));
        shapeRenderer.rect(panelX, panelY + PANEL_HEIGHT - 8f, PANEL_WIDTH, 8f);

        renderSettingButtonShape(panelX + 430f, panelY + 425f);
        renderSettingButtonShape(panelX + 430f, panelY + 355f);
        renderSettingButtonShape(panelX + 430f, panelY + 285f);
        renderSettingButtonShape(panelX + 430f, panelY + 215f);
        renderSettingButtonShape(panelX + 430f, panelY + 145f);

        shapeRenderer.setColor(new Color(0.55f, 0.18f, 0.18f, 1f));
        shapeRenderer.rect(panelX + 250f, panelY + 55f, 200f, 52f);

        shapeRenderer.end();
    }

    private void renderSettingButtonShape(float x, float y) {
        shapeRenderer.setColor(new Color(0.18f, 0.42f, 0.28f, 1f));
        shapeRenderer.rect(x, y, 190f, 48f);
    }

    private void renderPanelText(SettingsViewState state) {
        float panelX = getPanelX();
        float panelY = getPanelY();

        batch.begin();

        titleFont.draw(batch, "SETTINGS", panelX + 250f, panelY + PANEL_HEIGHT - 55f);

        font.draw(batch, "Player speed", panelX + 80f, panelY + 462f);
        font.draw(batch, state.getMovementSpeedLabel(), panelX + 475f, panelY + 460f);

        font.draw(batch, "Plant growth", panelX + 80f, panelY + 392f);
        font.draw(batch, state.getPlantGrowthSpeedLabel(), panelX + 475f, panelY + 390f);

        font.draw(batch, "Night cycle", panelX + 80f, panelY + 322f);
        font.draw(batch, onOff(state.isNightCycleEnabled()), panelX + 490f, panelY + 320f);

        font.draw(batch, "Quest panel", panelX + 80f, panelY + 252f);
        font.draw(batch, onOff(state.isQuestPanelVisible()), panelX + 490f, panelY + 250f);

        font.draw(batch, "Show FPS", panelX + 80f, panelY + 182f);
        font.draw(batch, onOff(state.isShowFps()), panelX + 490f, panelY + 180f);

        font.draw(batch, "RESET", panelX + 302f, panelY + 90f);
        font.draw(batch, "Press O to close", panelX + 205f, panelY + 35f);

        batch.end();
    }

    private void renderFpsIfEnabled(SettingsViewState state) {
        if (!state.isShowFps()) {
            return;
        }

        batch.begin();
        font.draw(
                batch,
                "FPS: " + Gdx.graphics.getFramesPerSecond(),
                camera.viewportWidth - 370f,
                camera.viewportHeight - 30f
        );
        batch.end();
    }

    private String onOff(boolean value) {
        if (value) {
            return "ON";
        }

        return "OFF";
    }

    private float getPanelX() {
        return (camera.viewportWidth - PANEL_WIDTH) / 2f;
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_HEIGHT) / 2f;
    }
}