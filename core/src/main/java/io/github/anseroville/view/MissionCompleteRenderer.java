package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.MissionCompleteViewState;

public class MissionCompleteRenderer {
    public static final float PANEL_WIDTH = 760f;
    public static final float PANEL_HEIGHT = 440f;

    public static final float CONTINUE_BUTTON_WIDTH = 260f;
    public static final float CONTINUE_BUTTON_HEIGHT = 64f;
    public static final float CONTINUE_BUTTON_Y_OFFSET = 72f;

    private static final Color OVERLAY_COLOR = new Color(0f, 0f, 0f, 0.72f);
    private static final Color PANEL_BACKGROUND_COLOR = new Color(0.04f, 0.07f, 0.05f, 1f);
    private static final Color PANEL_INNER_COLOR = new Color(0.10f, 0.18f, 0.10f, 1f);
    private static final Color GOLD_COLOR = new Color(0.95f, 0.72f, 0.25f, 1f);
    private static final Color GREEN_COLOR = new Color(0.18f, 0.42f, 0.22f, 1f);
    private static final Color TEXT_COLOR = new Color(0.94f, 0.96f, 0.91f, 1f);
    private static final Color MUTED_TEXT_COLOR = new Color(0.74f, 0.82f, 0.70f, 1f);

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final GlyphLayout glyphLayout;

    private final BitmapFont titleFont;
    private final BitmapFont textFont;
    private final BitmapFont smallFont;

    public MissionCompleteRenderer(
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
        this.glyphLayout = new GlyphLayout();

        this.titleFont = assetProvider.getBigFont();
        this.textFont = assetProvider.getMediumFont();
        this.smallFont = assetProvider.getSmallestFont();
    }

    public void render() {
        MissionCompleteViewState state = viewModel.getMissionCompleteViewState();

        if (!state.isOpen()) {
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        renderBackground();
        renderText(state);
    }

    private void renderBackground() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        float buttonX = getContinueButtonX();
        float buttonY = getContinueButtonY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(OVERLAY_COLOR);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        shapeRenderer.setColor(new Color(0f, 0f, 0f, 0.45f));
        shapeRenderer.rect(panelX + 10f, panelY - 10f, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(PANEL_BACKGROUND_COLOR);
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(GOLD_COLOR);
        shapeRenderer.rect(panelX, panelY + PANEL_HEIGHT - 10f, PANEL_WIDTH, 10f);
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, 8f);
        shapeRenderer.rect(panelX, panelY, 8f, PANEL_HEIGHT);
        shapeRenderer.rect(panelX + PANEL_WIDTH - 8f, panelY, 8f, PANEL_HEIGHT);

        shapeRenderer.setColor(PANEL_INNER_COLOR);
        shapeRenderer.rect(panelX + 24f, panelY + 24f, PANEL_WIDTH - 48f, PANEL_HEIGHT - 48f);

        shapeRenderer.setColor(new Color(0.12f, 0.30f, 0.14f, 1f));
        shapeRenderer.rect(panelX + 70f, panelY + 250f, PANEL_WIDTH - 140f, 86f);

        shapeRenderer.setColor(GREEN_COLOR);
        shapeRenderer.rect(buttonX, buttonY, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);

        shapeRenderer.setColor(GOLD_COLOR);
        shapeRenderer.rect(buttonX, buttonY + CONTINUE_BUTTON_HEIGHT - 6f, CONTINUE_BUTTON_WIDTH, 6f);

        shapeRenderer.end();
    }

    private void renderText(MissionCompleteViewState state) {
        float panelX = getPanelX();
        float panelY = getPanelY();

        batch.begin();

        titleFont.setColor(TEXT_COLOR);
        drawCentered(
                titleFont,
                "LEVEL " + state.getCompletedLevelNumber() + " COMPLETED",
                panelX,
                panelY + PANEL_HEIGHT - 92f,
                PANEL_WIDTH
        );

        textFont.setColor(GOLD_COLOR);
        drawCentered(
                textFont,
                "MAIN QUEST COMPLETED!",
                panelX,
                panelY + 298f,
                PANEL_WIDTH
        );

        smallFont.setColor(MUTED_TEXT_COLOR);

        if (state.isFinalLevel()) {
            drawCentered(
                    smallFont,
                    "You completed the last level. Great job!",
                    panelX,
                    panelY + 220f,
                    PANEL_WIDTH
            );
        } else {
            drawCentered(
                    smallFont,
                    "Click continue to start level " + state.getNextLevelNumber() + ".",
                    panelX,
                    panelY + 220f,
                    PANEL_WIDTH
            );
        }

        textFont.setColor(TEXT_COLOR);
        drawCentered(
                textFont,
                state.getContinueButtonText(),
                getContinueButtonX(),
                getContinueButtonY() + 43f,
                CONTINUE_BUTTON_WIDTH
        );

        smallFont.setColor(MUTED_TEXT_COLOR);
        drawCentered(
                smallFont,
                "You can also press ENTER.",
                panelX,
                panelY + 42f,
                PANEL_WIDTH
        );

        resetFontColors();

        batch.end();
    }

    private void drawCentered(
            BitmapFont font,
            String text,
            float x,
            float y,
            float width
    ) {
        glyphLayout.setText(font, text);
        float textX = x + (width - glyphLayout.width) / 2f;
        font.draw(batch, text, textX, y);
    }

    public float getContinueButtonX() {
        return getPanelX() + (PANEL_WIDTH - CONTINUE_BUTTON_WIDTH) / 2f;
    }

    public float getContinueButtonY() {
        return getPanelY() + CONTINUE_BUTTON_Y_OFFSET;
    }

    private float getPanelX() {
        return (camera.viewportWidth - PANEL_WIDTH) / 2f;
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_HEIGHT) / 2f;
    }

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
        smallFont.setColor(Color.WHITE);
    }
}