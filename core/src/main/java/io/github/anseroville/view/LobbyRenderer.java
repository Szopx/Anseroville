package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;

public class LobbyRenderer {
    public static final float BUTTON_WIDTH = 430f;
    public static final float BUTTON_HEIGHT = 76f;
    public static final float BUTTON_GAP = 22f;

    private static final float INFO_CARD_WIDTH = 420f;
    private static final float INFO_CARD_HEIGHT = 455f;

    private static final float MENU_PANEL_WIDTH = 520f;
    private static final float MENU_PANEL_HEIGHT = 450f;

    private static final float CORNER_RADIUS = 24f;
    private static final float BUTTON_RADIUS = 22f;

    private static final Color SCREEN_DARK_OVERLAY = new Color(0f, 0f, 0f, 0.48f);
    private static final Color TOP_GRADIENT = new Color(0.02f, 0.035f, 0.02f, 0.78f);
    private static final Color BOTTOM_GRADIENT = new Color(0.02f, 0.035f, 0.02f, 0.70f);

    private static final Color WOOD_DARK = new Color(0.18f, 0.09f, 0.035f, 0.94f);
    private static final Color WOOD_MID = new Color(0.30f, 0.16f, 0.06f, 0.94f);
    private static final Color PANEL_GREEN = new Color(0.055f, 0.16f, 0.075f, 0.94f);
    private static final Color PANEL_GREEN_INNER = new Color(0.085f, 0.22f, 0.10f, 0.95f);

    private static final Color GOLD = new Color(0.96f, 0.72f, 0.22f, 1f);
    private static final Color GOLD_DARK = new Color(0.55f, 0.32f, 0.08f, 1f);
    private static final Color GREEN = new Color(0.18f, 0.48f, 0.20f, 0.98f);
    private static final Color GREEN_LIGHT = new Color(0.28f, 0.62f, 0.25f, 0.96f);
    private static final Color GREEN_DARK = new Color(0.06f, 0.22f, 0.08f, 0.98f);
    private static final Color RED = new Color(0.55f, 0.15f, 0.10f, 0.98f);
    private static final Color RED_LIGHT = new Color(0.70f, 0.22f, 0.15f, 0.96f);
    private static final Color RED_DARK = new Color(0.30f, 0.06f, 0.04f, 0.98f);

    private static final Color SHADOW = new Color(0f, 0f, 0f, 0.55f);
    private static final Color TEXT = new Color(0.98f, 0.96f, 0.88f, 1f);
    private static final Color TEXT_MUTED = new Color(0.78f, 0.86f, 0.72f, 1f);

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final AssetProvider assetProvider;
    private final GlyphLayout glyphLayout;

    private final BitmapFont titleFont;
    private final BitmapFont textFont;
    private final BitmapFont smallFont;

    public LobbyRenderer(
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
        this.assetProvider = assetProvider;
        this.glyphLayout = new GlyphLayout();

        this.titleFont = assetProvider.getBigFont();
        this.textFont = assetProvider.getMediumFont();
        this.smallFont = assetProvider.getSmallestFont();
    }

    public void render() {
        if (!viewModel.isLobbyOpen()) {
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        renderBackground();
        renderPanels();
        renderText();

        resetFontColors();
    }

    private void renderBackground() {
        Texture background = assetProvider.getLobbyBackgroundTexture();

        batch.begin();
        drawCover(background);
        batch.end();

        beginShapes();

        shapeRenderer.setColor(SCREEN_DARK_OVERLAY);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        shapeRenderer.setColor(TOP_GRADIENT);
        shapeRenderer.rect(0, camera.viewportHeight - 150f, camera.viewportWidth, 150f);

        shapeRenderer.setColor(BOTTOM_GRADIENT);
        shapeRenderer.rect(0, 0, camera.viewportWidth, 95f);

        shapeRenderer.setColor(GOLD);
        shapeRenderer.rect(0, camera.viewportHeight - 6f, camera.viewportWidth, 6f);
        shapeRenderer.rect(0, 0, camera.viewportWidth, 6f);

        endShapes();
    }

    private void renderPanels() {
        beginShapes();

        renderTitleOrnament();
        renderInfoCard();
        renderMenuPanel();

        renderMenuButton(
                getButtonX(camera.viewportWidth),
                getStartButtonY(camera.viewportHeight),
                GREEN_DARK,
                GREEN,
                GREEN_LIGHT
        );

        renderMenuButton(
                getButtonX(camera.viewportWidth),
                getHelpButtonY(camera.viewportHeight),
                GREEN_DARK,
                GREEN,
                GREEN_LIGHT
        );

        renderMenuButton(
                getButtonX(camera.viewportWidth),
                getSettingsButtonY(camera.viewportHeight),
                GREEN_DARK,
                GREEN,
                GREEN_LIGHT
        );

        renderMenuButton(
                getButtonX(camera.viewportWidth),
                getExitButtonY(camera.viewportHeight),
                RED_DARK,
                RED,
                RED_LIGHT
        );

        endShapes();
    }

    private void renderTitleOrnament() {
        float centerX = camera.viewportWidth / 2f;
        float y = camera.viewportHeight - 132f;

        shapeRenderer.setColor(GOLD_DARK);
        shapeRenderer.rect(centerX - 220f, y, 440f, 4f);

        shapeRenderer.setColor(GOLD);
        shapeRenderer.circle(centerX - 240f, y + 2f, 7f);
        shapeRenderer.circle(centerX + 240f, y + 2f, 7f);
        shapeRenderer.circle(centerX, y + 2f, 9f);
    }

    private void renderInfoCard() {
        float x = getInfoCardX(camera.viewportWidth);
        float y = getInfoCardY(camera.viewportHeight);

        drawRoundedRect(x + 12f, y - 12f, INFO_CARD_WIDTH, INFO_CARD_HEIGHT, CORNER_RADIUS, SHADOW);

        drawRoundedRect(x, y, INFO_CARD_WIDTH, INFO_CARD_HEIGHT, CORNER_RADIUS, GOLD_DARK);
        drawRoundedRect(x + 6f, y + 6f, INFO_CARD_WIDTH - 12f, INFO_CARD_HEIGHT - 12f, CORNER_RADIUS - 4f, WOOD_DARK);
        drawRoundedRect(x + 20f, y + 20f, INFO_CARD_WIDTH - 40f, INFO_CARD_HEIGHT - 40f, CORNER_RADIUS - 8f, PANEL_GREEN);

        drawRoundedRect(x + 42f, y + INFO_CARD_HEIGHT - 92f, INFO_CARD_WIDTH - 84f, 52f, 18f, WOOD_MID);

        renderInfoBullet(x + 58f, y + 190f, GOLD);
        renderInfoBullet(x + 58f, y + 140f, GOLD);
        renderInfoBullet(x + 58f, y + 90f, GOLD);
    }

    private void renderInfoBullet(float x, float y, Color color) {
        shapeRenderer.setColor(GOLD_DARK);
        shapeRenderer.circle(x, y, 17f);

        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, 12f);

        shapeRenderer.setColor(PANEL_GREEN_INNER);
        shapeRenderer.circle(x, y, 5f);
    }

    private void renderMenuPanel() {
        float x = getMenuPanelX(camera.viewportWidth);
        float y = getMenuPanelY(camera.viewportHeight);

        drawRoundedRect(x + 12f, y - 12f, MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT, CORNER_RADIUS, SHADOW);

        drawRoundedRect(x, y, MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT, CORNER_RADIUS, GOLD_DARK);
        drawRoundedRect(x + 6f, y + 6f, MENU_PANEL_WIDTH - 12f, MENU_PANEL_HEIGHT - 12f, CORNER_RADIUS - 4f, WOOD_DARK);
        drawRoundedRect(x + 20f, y + 20f, MENU_PANEL_WIDTH - 40f, MENU_PANEL_HEIGHT - 40f, CORNER_RADIUS - 8f, new Color(0.025f, 0.055f, 0.03f, 0.88f));

        shapeRenderer.setColor(GOLD);
        shapeRenderer.rect(x + 72f, y + MENU_PANEL_HEIGHT - 54f, MENU_PANEL_WIDTH - 144f, 4f);
    }

    private void renderMenuButton(
            float x,
            float y,
            Color darkColor,
            Color mainColor,
            Color lightColor
    ) {
        drawRoundedRect(x + 8f, y - 8f, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_RADIUS, SHADOW);

        drawRoundedRect(x - 5f, y - 5f, BUTTON_WIDTH + 10f, BUTTON_HEIGHT + 10f, BUTTON_RADIUS + 4f, GOLD_DARK);
        drawRoundedRect(x - 1f, y - 1f, BUTTON_WIDTH + 2f, BUTTON_HEIGHT + 2f, BUTTON_RADIUS + 2f, GOLD);

        drawRoundedRect(x + 5f, y + 5f, BUTTON_WIDTH - 10f, BUTTON_HEIGHT - 10f, BUTTON_RADIUS, darkColor);
        drawRoundedRect(x + 13f, y + 13f, BUTTON_WIDTH - 26f, BUTTON_HEIGHT - 26f, BUTTON_RADIUS - 6f, mainColor);

        shapeRenderer.setColor(lightColor);
        shapeRenderer.rect(x + 38f, y + BUTTON_HEIGHT - 24f, BUTTON_WIDTH - 76f, 7f);

        shapeRenderer.setColor(new Color(1f, 1f, 1f, 0.10f));
        shapeRenderer.rect(x + 34f, y + BUTTON_HEIGHT - 36f, BUTTON_WIDTH - 68f, 10f);
    }

    private void renderText() {
        float buttonX = getButtonX(camera.viewportWidth);
        float infoX = getInfoCardX(camera.viewportWidth);
        float infoY = getInfoCardY(camera.viewportHeight);

        batch.begin();

        titleFont.setColor(TEXT);
        drawCentered(
                titleFont,
                "ANSEROVILLE",
                0,
                camera.viewportHeight - 54f,
                camera.viewportWidth
        );

        smallFont.setColor(TEXT_MUTED);
        drawCentered(
                smallFont,
                "A cozy farming adventure about crops, quests and progress.",
                0,
                camera.viewportHeight - 96f,
                camera.viewportWidth
        );

        textFont.setColor(TEXT);
        drawCentered(textFont, "WELCOME FARMER", infoX, infoY + INFO_CARD_HEIGHT - 58f, INFO_CARD_WIDTH);

        smallFont.setColor(TEXT_MUTED);
        smallFont.draw(batch, "Build your farm step by step.", infoX + 52f, infoY + 300f);
        smallFont.draw(batch, "Plant seeds, water crops and", infoX + 52f, infoY + 270f);
        smallFont.draw(batch, "collect harvests to complete quests.", infoX + 52f, infoY + 240f);

        smallFont.setColor(TEXT);
        smallFont.draw(batch, "Grow crops", infoX + 90f, infoY + 198f);
        smallFont.draw(batch, "Complete quests", infoX + 90f, infoY + 148f);
        smallFont.draw(batch, "Unlock new levels", infoX + 90f, infoY + 98f);

        smallFont.setColor(GOLD);
        smallFont.draw(batch, "Main quests move you forward.", infoX + 52f, infoY + 48f);

        textFont.setColor(TEXT);
        drawCentered(textFont, "START GAME", buttonX, getStartButtonY(camera.viewportHeight) + 47f, BUTTON_WIDTH);
        drawCentered(textFont, "HOW TO PLAY", buttonX, getHelpButtonY(camera.viewportHeight) + 47f, BUTTON_WIDTH);
        drawCentered(textFont, "SETTINGS", buttonX, getSettingsButtonY(camera.viewportHeight) + 47f, BUTTON_WIDTH);
        drawCentered(textFont, "EXIT", buttonX, getExitButtonY(camera.viewportHeight) + 47f, BUTTON_WIDTH);

        smallFont.setColor(TEXT_MUTED);
        drawCentered(
                smallFont,
                "ENTER - Start    ESC - Help    O - Settings",
                0,
                38f,
                camera.viewportWidth
        );

        batch.end();
    }

    private void drawCover(Texture texture) {
        float textureWidth = texture.getWidth();
        float textureHeight = texture.getHeight();

        float scale = Math.max(
                camera.viewportWidth / textureWidth,
                camera.viewportHeight / textureHeight
        );

        float drawWidth = textureWidth * scale;
        float drawHeight = textureHeight * scale;

        float drawX = (camera.viewportWidth - drawWidth) / 2f;
        float drawY = (camera.viewportHeight - drawHeight) / 2f;

        batch.draw(texture, drawX, drawY, drawWidth, drawHeight);
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

    public static float getButtonX(float viewportWidth) {
        return (viewportWidth - BUTTON_WIDTH) / 2f;
    }

    public static float getStartButtonY(float viewportHeight) {
        return viewportHeight * 0.49f;
    }

    public static float getHelpButtonY(float viewportHeight) {
        return getStartButtonY(viewportHeight) - BUTTON_HEIGHT - BUTTON_GAP;
    }

    public static float getSettingsButtonY(float viewportHeight) {
        return getHelpButtonY(viewportHeight) - BUTTON_HEIGHT - BUTTON_GAP;
    }

    public static float getExitButtonY(float viewportHeight) {
        return getSettingsButtonY(viewportHeight) - BUTTON_HEIGHT - BUTTON_GAP;
    }

    private static float getMenuPanelX(float viewportWidth) {
        return getButtonX(viewportWidth) - 45f;
    }

    private static float getMenuPanelY(float viewportHeight) {
        return getExitButtonY(viewportHeight) - 48f;
    }

    private static float getInfoCardX(float viewportWidth) {
        return Math.max(60f, viewportWidth * 0.075f);
    }

    private static float getInfoCardY(float viewportHeight) {
        return (viewportHeight - INFO_CARD_HEIGHT) / 2f - 4f;
    }

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
        smallFont.setColor(Color.WHITE);
    }
}