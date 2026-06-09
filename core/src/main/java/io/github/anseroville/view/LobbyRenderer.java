package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.viewModel.FarmViewModel;

public class LobbyRenderer {

    // ── Layout ────────────────────────────────────────────────────────────────

    // Title image (title.png) drawn centered near the top
    private static final float TITLE_W = 1000f;
    private static final float TITLE_H = 400f;   // adjust to match your asset's aspect ratio

    // Side panel (side.png) on the left
    private static final float PANEL_W = 400f;
    private static final float PANEL_H = 610f;

    // Button (button.png) — each button is one copy of this texture
    public static final float BUTTON_WIDTH = 600f;
    public static final float BUTTON_HEIGHT = 120f;
    public static final float BUTTON_GAP = 14f;

    // ── Text colours ──────────────────────────────────────────────────────────
    private static final Color TEXT       = new Color(0.97f, 0.94f, 0.84f, 1f);
    private static final Color TEXT_MUTED = new Color(0.76f, 0.84f, 0.68f, 1f);
    private static final Color TEXT_GOLD  = new Color(0.95f, 0.80f, 0.40f, 1f);

    // ── Fields ────────────────────────────────────────────────────────────────
    private final FarmViewModel      viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch        batch;
    private final AssetProvider      assetProvider;
    private final GlyphLayout        glyphLayout;

    private final BitmapFont titleFont;   // big   — game title
    private final BitmapFont textFont;    // medium — button labels, card header
    private final BitmapFont smallFont;   // small  — body copy, shortcuts

    // ── Constructor ───────────────────────────────────────────────────────────
    public LobbyRenderer(
            FarmViewModel viewModel,
            OrthographicCamera camera,
            SpriteBatch batch,
            AssetProvider assetProvider
    ) {
        this.viewModel     = viewModel;
        this.camera        = camera;
        this.batch         = batch;
        this.assetProvider = assetProvider;
        this.glyphLayout   = new GlyphLayout();
        this.titleFont     = assetProvider.getBigFont();
        this.textFont      = assetProvider.getMediumFont();
        this.smallFont     = assetProvider.getSmallestFont();
    }

    private boolean isInside(
            float clickX,
            float clickY,
            float x,
            float y,
            float width,
            float height
    ) {
        return clickX >= x
                && clickX <= x + width
                && clickY >= y
                && clickY <= y + height;
    }
    // ── Public entry point ────────────────────────────────────────────────────
    public void render() {
        if (!viewModel.isLobbyOpen()) return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackground();
        drawTitle();
        drawSidePanel();
        drawButtons();
        drawText();

        batch.end();

        resetFontColors();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Draw background
    // ─────────────────────────────────────────────────────────────────────────
    private void drawBackground() {
        Texture bg = assetProvider.getLobbyBackgroundTexture();

        float tw    = bg.getWidth();
        float th    = bg.getHeight();
        float scale = Math.max(camera.viewportWidth / tw, camera.viewportHeight / th);
        float dw    = tw * scale;
        float dh    = th * scale;

        batch.draw(bg,
                (camera.viewportWidth  - dw) / 2f,
                (camera.viewportHeight - dh) / 2f,
                dw, dh);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Draw title image (title.png) — centered horizontally near the top
    // ─────────────────────────────────────────────────────────────────────────
    private void drawTitle() {
        Texture title = assetProvider.getTitleTexture();   // title.png

        float x = (camera.viewportWidth - TITLE_W) / 2f;
        float y = camera.viewportHeight - TITLE_H - 40f;  // 10px gap from top

        batch.draw(title, x, y, TITLE_W, TITLE_H);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Draw side panel image (side.png) — left side, vertically centered
    // ─────────────────────────────────────────────────────────────────────────
    private void drawSidePanel() {
        Texture panel = assetProvider.getSidePanelTexture();   // side.png

        float x = getPanelX();
        float y = getPanelY();

        batch.draw(panel, x, y, PANEL_W, PANEL_H);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Draw four buttons (button.png reused for each)
    // ─────────────────────────────────────────────────────────────────────────
    private void drawButtons() {
        Texture btn = assetProvider.getButtonTexture();   // button.png

        float x = getButtonX(camera.viewportWidth);

        batch.draw(btn, x, getStartButtonY(camera.viewportHeight),    BUTTON_WIDTH, BUTTON_HEIGHT);
        batch.draw(btn, x, getHelpButtonY(camera.viewportHeight),     BUTTON_WIDTH, BUTTON_HEIGHT);
        batch.draw(btn, x, getSettingsButtonY(camera.viewportHeight), BUTTON_WIDTH, BUTTON_HEIGHT);
        batch.draw(btn, x, getExitButtonY(camera.viewportHeight),     BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Draw all text on top of the textures
    // ─────────────────────────────────────────────────────────────────────────
    private void drawText() {
        float W   = camera.viewportWidth;
        float H   = camera.viewportHeight;
        float px  = getPanelX();
        float py  = getPanelY();
        float bx  = getButtonX(W);

        // ── Panel: header ────────────────────────────────────────────────────
        textFont.setColor(TEXT);
        drawCentered(textFont, "WELCOME, FARMER!", px, py + PANEL_H - 130f, PANEL_W);

        // ── Panel: description ────────────────────────────────────────────────
        smallFont.setColor(TEXT_MUTED);
        float descX = px + 24f;
        smallFont.draw(batch, "Build your farm step by step.", descX+30, py + PANEL_H - 180f);
        smallFont.draw(batch, "Plant seeds, water crops and",  descX+30, py + PANEL_H - 200f);
        smallFont.draw(batch, "harvest them.",                  descX+30, py + PANEL_H - 220f);
        smallFont.draw(batch, "Complete quests to help",        descX+30, py + PANEL_H - 240f);
        smallFont.draw(batch, "your goose friends.",            descX+30, py + PANEL_H - 260f);

        // ── Panel: bullet list ────────────────────────────────────────────────
        smallFont.setColor(TEXT);
        float[] bys    = getBulletYs(py);
        float offset = - 150f;
        String[] items = { "GROW CROPS", "WATER AND CARE", "HARVEST CROPS", "COMPLETE QUESTS", "UNLOCK NEW AREAS" };
        for (int i = 0; i < items.length; i++) {
            smallFont.draw(batch, items[i], px + +30, bys[i] + 6f+offset);
        }

        // ── Panel: footer note ────────────────────────────────────────────────
        smallFont.setColor(TEXT_GOLD);
        drawCentered(smallFont, "Main quests move you forward.", px, py + 46f, PANEL_W);

        // ── Button labels (centered in each button) ───────────────────────────
        textFont.setColor(TEXT);
        drawCentered(textFont, "START GAME",  bx, getStartButtonY(H)    + BUTTON_HEIGHT / 2f + 9f, BUTTON_WIDTH);
        drawCentered(textFont, "HOW TO PLAY", bx, getHelpButtonY(H)     + BUTTON_HEIGHT / 2f + 9f, BUTTON_WIDTH);
        drawCentered(textFont, "SETTINGS",    bx, getSettingsButtonY(H) + BUTTON_HEIGHT / 2f + 9f, BUTTON_WIDTH);
        drawCentered(textFont, "EXIT GAME",   bx, getExitButtonY(H)     + BUTTON_HEIGHT / 2f + 9f, BUTTON_WIDTH);

        // ── Bottom shortcuts ──────────────────────────────────────────────────
        smallFont.setColor(TEXT_MUTED);
        drawCentered(smallFont, "ENTER \u2013 Start    ESC \u2013 Exit    F1 \u2013 Help", 0, 32f, W);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────
    private float[] getBulletYs(float panelY) {
        float startY = panelY + PANEL_H - 216f;
        float step   = 40f;
        return new float[] {
                startY,
                startY - step,
                startY - step * 2f,
                startY - step * 3f,
                startY - step * 4f
        };
    }

    private void drawCentered(BitmapFont font, String text, float x, float y, float width) {
        glyphLayout.setText(font, text);
        font.draw(batch, text, x + (width - glyphLayout.width) / 2f, y);
    }

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
        smallFont.setColor(Color.WHITE);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Layout — static overloads kept public for the input handler
    // ─────────────────────────────────────────────────────────────────────────
    private float getPanelX() {
        return Math.max(40f, camera.viewportWidth * 0.75f);
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_H) / 2f - 100f;
    }

    public static float getButtonX(float viewportWidth) {
        return (viewportWidth - BUTTON_WIDTH) / 2f;
    }

    public static float getStartButtonY(float viewportHeight) {
        return viewportHeight * 0.50f;
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
}