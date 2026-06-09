package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.GameEndViewState;

public class GameEndRenderer {
    public static final float PANEL_WIDTH = 820f;
    public static final float PANEL_HEIGHT = 500f;

    public static final float BUTTON_WIDTH = 340f;
    public static final float BUTTON_HEIGHT = 72f;
    public static final float BUTTON_Y_OFFSET = 86f;

    private static final float CORNER_RADIUS = 28f;
    private static final float BUTTON_RADIUS = 22f;

    private static final Color SCREEN_OVERLAY = new Color(0f, 0f, 0f, 0.66f);
    private static final Color SHADOW = new Color(0f, 0f, 0f, 0.58f);

    private static final Color WOOD_DARK = new Color(0.18f, 0.09f, 0.035f, 0.96f);
    private static final Color WOOD_MID = new Color(0.30f, 0.16f, 0.06f, 0.94f);
    private static final Color PANEL_GREEN = new Color(0.045f, 0.13f, 0.06f, 0.96f);
    private static final Color PANEL_GREEN_INNER = new Color(0.075f, 0.20f, 0.09f, 0.96f);
    static float p = -0.30f;
    private static final Color GOLD = new Color(0.96f+p, 0.72f+p, 0.22f+p, 1f);
    private static final Color GOLD_DARK = new Color(0.55f+p, 0.32f+p, 0.08f+p, 1f);
    private static final Color GREEN = new Color(0.18f+p, 0.48f+p, 0.20f+p, 0.98f);
    private static final Color GREEN_DARK = new Color(0.06f+p, 0.22f+p, 0.08f+p, 0.98f);
    private static final Color GREEN_LIGHT = new Color(0.28f+p, 0.62f+p, 0.25f+p, 0.96f);

    private static final Color TEXT = new Color(0.98f, 0.96f, 0.88f, 1f);
    private static final Color TEXT_MUTED = new Color(0.78f, 0.86f, 0.72f, 1f);

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final GlyphLayout glyphLayout;

    private final BitmapFont titleFont;
    private final BitmapFont textFont;
    private final BitmapFont smallFont;
    private final AssetProvider assetProvider;

    public GameEndRenderer(
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
        this.assetProvider = assetProvider;
    }

    public void render() {
        GameEndViewState state = viewModel.getGameEndViewState();

        if (!state.isOpen()) {
            return;
        }
        batch.begin();
        batch.draw(assetProvider.comix6, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        renderBackground();
        renderText();

        resetFontColors();
    }

    private void renderBackground() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        beginShapes();

        renderButton(getButtonX(), getButtonY());

        //renderOrnaments(panelX, panelY);

        endShapes();
    }

    private void renderButton(float x, float y) {
        drawRoundedRect(x + 8f, y - 8f, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_RADIUS, SHADOW);

        drawRoundedRect(x - 5f, y - 5f, BUTTON_WIDTH + 10f, BUTTON_HEIGHT + 10f, BUTTON_RADIUS + 4f, GOLD_DARK);
        drawRoundedRect(x - 1f, y - 1f, BUTTON_WIDTH + 2f, BUTTON_HEIGHT + 2f, BUTTON_RADIUS + 2f, GOLD);

        drawRoundedRect(x + 5f, y + 5f, BUTTON_WIDTH - 10f, BUTTON_HEIGHT - 10f, BUTTON_RADIUS, GREEN_DARK);
        drawRoundedRect(x + 13f, y + 13f, BUTTON_WIDTH - 26f, BUTTON_HEIGHT - 26f, BUTTON_RADIUS - 6f, GREEN);

        shapeRenderer.setColor(GREEN_LIGHT);
        shapeRenderer.rect(x + 34f, y + BUTTON_HEIGHT - 25f, BUTTON_WIDTH - 68f, 7f);
    }

    private void renderOrnaments(float panelX, float panelY) {
        float centerX = panelX + PANEL_WIDTH / 2f;
        float y = panelY + PANEL_HEIGHT - 120f;

        shapeRenderer.setColor(GOLD_DARK);
        shapeRenderer.rect(centerX - 220f, y, 440f, 4f);

        shapeRenderer.setColor(GOLD);
        shapeRenderer.circle(centerX - 240f, y + 2f, 7f);
        shapeRenderer.circle(centerX + 240f, y + 2f, 7f);
        shapeRenderer.circle(centerX, y + 2f, 9f);

        shapeRenderer.setColor(WOOD_MID);
        shapeRenderer.rect(panelX + 170f, panelY + 44f, PANEL_WIDTH - 340f, 5f);
    }

    private void renderText() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        batch.begin();

        textFont.setColor(TEXT);
        drawCentered(textFont, "BACK TO LOBBY", getButtonX(), getButtonY() + 46f, BUTTON_WIDTH);

        //smallFont.setColor(TEXT_MUTED);
        //drawCentered(smallFont, "Press ENTER or click the button.", panelX, panelY + 42f, PANEL_WIDTH);

        batch.end();
    }

    private void drawRoundedRect(
            float x,
            float y,
            float width,
            float height,
            float radius,
            Color color
    ) {
        shapeRenderer.setColor(color);

        shapeRenderer.rect(x + radius, y, width - radius * 2f, height);
        shapeRenderer.rect(x, y + radius, width, height - radius * 2f);

        shapeRenderer.circle(x + radius, y + radius, radius);
        shapeRenderer.circle(x + width - radius, y + radius, radius);
        shapeRenderer.circle(x + radius, y + height - radius, radius);
        shapeRenderer.circle(x + width - radius, y + height - radius, radius);
    }

    private void beginShapes() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    private void endShapes() {
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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

    public static float getPanelX(float viewportWidth) {
        return (viewportWidth - PANEL_WIDTH) / 2f;
    }

    public static float getPanelY(float viewportHeight) {
        return (viewportHeight - PANEL_HEIGHT) / 2f;
    }

    public static float getButtonX(float viewportWidth) {
        return getPanelX(viewportWidth) + (PANEL_WIDTH - BUTTON_WIDTH) / 2f-340;
    }

    public static float getButtonY(float viewportHeight) {
        return getPanelY(viewportHeight) + BUTTON_Y_OFFSET-340;
    }

    private float getPanelX() {
        return getPanelX(camera.viewportWidth);
    }

    private float getPanelY() {
        return getPanelY(camera.viewportHeight);
    }

    private float getButtonX() {
        return getButtonX(camera.viewportWidth);
    }

    private float getButtonY() {
        return getButtonY(camera.viewportHeight);
    }

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
        smallFont.setColor(Color.WHITE);
    }
}