package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;

public class HelpRenderer {
    private static final float PANEL_WIDTH = 1040f;
    private static final float PANEL_HEIGHT = 700f;

    private static final float PANEL_PADDING = 56f;
    private static final float CARD_GAP = 42f;

    private static final float INTRO_HEIGHT = 92f;
    private static final float CARD_HEIGHT = 305f;
    private static final float FOOTER_HEIGHT = 92f;

    private static final Color OVERLAY_COLOR = new Color(0f, 0f, 0f, 0.78f);
    private static final Color PANEL_SHADOW_COLOR = new Color(0f, 0f, 0f, 0.45f);
    private static final Color PANEL_BORDER_COLOR = new Color(0.92f, 0.66f, 0.18f, 1f);
    private static final Color PANEL_BACKGROUND_COLOR = new Color(0.04f, 0.07f, 0.05f, 1f);
    private static final Color PANEL_INNER_COLOR = new Color(0.09f, 0.17f, 0.10f, 1f);
    private static final Color CARD_BACKGROUND_COLOR = new Color(0.035f, 0.07f, 0.045f, 1f);
    private static final Color CARD_HEADER_COLOR = new Color(0.12f, 0.36f, 0.13f, 1f);
    private static final Color CARD_LINE_COLOR = new Color(0.17f, 0.28f, 0.16f, 1f);
    private static final Color TEXT_COLOR = new Color(0.94f, 0.96f, 0.91f, 1f);
    private static final Color MUTED_TEXT_COLOR = new Color(0.74f, 0.82f, 0.70f, 1f);
    private static final Color GOLD_TEXT_COLOR = new Color(1f, 0.78f, 0.30f, 1f);

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final GlyphLayout glyphLayout;

    private final BitmapFont titleFont;
    private final BitmapFont sectionFont;
    private final BitmapFont textFont;

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
        this.glyphLayout = new GlyphLayout();

        this.titleFont = assetProvider.getBigFont();
        this.sectionFont = assetProvider.getSmallFont();
        this.textFont = assetProvider.getSmallestFont();
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

        float innerX = panelX + 20f;
        float innerY = panelY + 20f;
        float innerWidth = PANEL_WIDTH - 40f;
        float innerHeight = PANEL_HEIGHT - 40f;

        float contentX = panelX + PANEL_PADDING;
        float contentY = panelY + PANEL_PADDING;
        float contentWidth = PANEL_WIDTH - PANEL_PADDING * 2f;

        float introY = panelY + PANEL_HEIGHT - 205f;
        float cardY = introY - CARD_HEIGHT - CARD_GAP;
        float cardWidth = (contentWidth - CARD_GAP) / 2f;
        float footerY = contentY;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(OVERLAY_COLOR);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        shapeRenderer.setColor(PANEL_SHADOW_COLOR);
        shapeRenderer.rect(panelX + 10f, panelY - 10f, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(PANEL_BACKGROUND_COLOR);
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        shapeRenderer.setColor(PANEL_BORDER_COLOR);
        shapeRenderer.rect(panelX, panelY + PANEL_HEIGHT - 10f, PANEL_WIDTH, 10f);
        shapeRenderer.rect(panelX, panelY, PANEL_WIDTH, 8f);
        shapeRenderer.rect(panelX, panelY, 8f, PANEL_HEIGHT);
        shapeRenderer.rect(panelX + PANEL_WIDTH - 8f, panelY, 8f, PANEL_HEIGHT);

        shapeRenderer.setColor(PANEL_INNER_COLOR);
        shapeRenderer.rect(innerX, innerY, innerWidth, innerHeight);

        renderCardBackground(contentX, introY, contentWidth, INTRO_HEIGHT, false);
        renderCardBackground(contentX, cardY, cardWidth, CARD_HEIGHT, true);
        renderCardBackground(contentX + cardWidth + CARD_GAP, cardY, cardWidth, CARD_HEIGHT, true);
        renderCardBackground(contentX, footerY, contentWidth, FOOTER_HEIGHT, false);

        shapeRenderer.end();
    }

    private void renderCardBackground(
            float x,
            float y,
            float width,
            float height,
            boolean withHeader
    ) {
        shapeRenderer.setColor(CARD_BACKGROUND_COLOR);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(CARD_LINE_COLOR);
        shapeRenderer.rect(x, y, width, 3f);

        if (!withHeader) {
            return;
        }

        shapeRenderer.setColor(CARD_HEADER_COLOR);
        shapeRenderer.rect(x, y + height - 58f, width, 58f);
    }

    private void renderText() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        float contentX = panelX + PANEL_PADDING;
        float contentWidth = PANEL_WIDTH - PANEL_PADDING * 2f;

        float introY = panelY + PANEL_HEIGHT - 205f;
        float cardY = introY - CARD_HEIGHT - CARD_GAP;
        float cardWidth = (contentWidth - CARD_GAP) / 2f;
        float rightCardX = contentX + cardWidth + CARD_GAP;
        float footerY = panelY + PANEL_PADDING;

        batch.begin();

        titleFont.setColor(TEXT_COLOR);
        drawCentered(titleFont, "HOW TO PLAY", panelX, panelY + PANEL_HEIGHT - 62f, PANEL_WIDTH);

        textFont.setColor(MUTED_TEXT_COLOR);
        drawCentered(textFont, "Grow crops, complete quests and unlock new levels.", contentX, introY + 61f, contentWidth);
        drawCentered(textFont, "Side quests give rewards. Main quests move you forward.", contentX, introY + 32f, contentWidth);

        drawSectionTitle("FARMING", contentX + 32f, cardY + CARD_HEIGHT - 22f);
        drawControlRow("ARROWS", "Move around the farm", contentX + 42f, cardY + 213f);
        drawControlRow("H", "Plant selected seed", contentX + 42f, cardY + 155f);
        drawControlRow("W", "Water planted crop", contentX + 42f, cardY + 97f);
        drawControlRow("C", "Collect grown crop", contentX + 42f, cardY + 39f);

        drawSectionTitle("INVENTORY & QUESTS", rightCardX + 32f, cardY + CARD_HEIGHT - 22f);
        drawControlRow("I", "Open inventory", rightCardX + 42f, cardY + 213f);
        drawControlRow("E", "Complete side quest", rightCardX + 42f, cardY + 155f);
        drawControlRow("M", "Complete main quest", rightCardX + 42f, cardY + 97f);
        drawControlRow("O", "Open settings", rightCardX + 42f, cardY + 39f);

        textFont.setColor(TEXT_COLOR);
        drawCentered(textFont, "ESC  -  open or close this help screen", contentX, footerY + 60f, contentWidth);

        textFont.setColor(GOLD_TEXT_COLOR);
        drawCentered(textFont, "Tip: crops appear after planting, but they grow only after watering.", contentX, footerY + 29f, contentWidth);

        resetFontColors();

        batch.end();
    }

    private void drawSectionTitle(String title, float x, float y) {
        sectionFont.setColor(TEXT_COLOR);
        sectionFont.draw(batch, title, x, y);
    }

    private void drawControlRow(String key, String description, float x, float y) {
        float keyX = x;
        float separatorX = x + 118f;
        float descriptionX = x + 154f;

        textFont.setColor(GOLD_TEXT_COLOR);
        textFont.draw(batch, key, keyX, y);

        textFont.setColor(MUTED_TEXT_COLOR);
        textFont.draw(batch, "-", separatorX, y);

        textFont.setColor(TEXT_COLOR);
        textFont.draw(batch, description, descriptionX, y);
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

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        sectionFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
    }

    private float getPanelX() {
        return (camera.viewportWidth - PANEL_WIDTH) / 2f;
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_HEIGHT) / 2f;
    }
}