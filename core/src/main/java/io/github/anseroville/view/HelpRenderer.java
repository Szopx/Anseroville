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

public class HelpRenderer {
    private static final float PANEL_WIDTH = 1040f;
    private static final float PANEL_HEIGHT = 650f;

    private static final float CARD_WIDTH = 430f;
    private static final float CARD_HEIGHT = 280f;
    private static final float CARD_GAP = 42f;

    private static final float CORNER_RADIUS = 26f;
    private static final float CARD_RADIUS = 18f;

    private static final Color SCREEN_OVERLAY = new Color(0f, 0f, 0f, 0.66f);

    private static final Color SHADOW = new Color(0f, 0f, 0f, 0.55f);
    private static final Color WOOD_TINT = new Color(0.72f, 0.48f, 0.25f, 0.96f);
    private static final Color WOOD_DARK_OVERLAY = new Color(0.10f, 0.045f, 0.015f, 0.42f);

    private static final Color GOLD = new Color(0.96f, 0.72f, 0.22f, 1f);
    private static final Color GOLD_DARK = new Color(0.48f, 0.27f, 0.07f, 1f);

    private static final Color PAPER = new Color(0.78f, 0.63f, 0.38f, 0.94f);
    private static final Color PAPER_DARK = new Color(0.38f, 0.23f, 0.10f, 0.95f);
    private static final Color PAPER_INNER = new Color(0.95f, 0.84f, 0.58f, 0.96f);

    private static final Color GREEN_DARK = new Color(0.04f, 0.14f, 0.06f, 0.95f);

    private static final Color TEXT_DARK = new Color(0.14f, 0.08f, 0.035f, 1f);
    private static final Color TEXT_LIGHT = new Color(0.98f, 0.95f, 0.83f, 1f);

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final AssetProvider assetProvider;
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
        this.assetProvider = assetProvider;
        this.glyphLayout = new GlyphLayout();

        this.titleFont = assetProvider.getBigFont();
        this.sectionFont = assetProvider.getMediumFont();
        this.textFont = assetProvider.getSmallestFont();
    }

    public void render() {
        if (!viewModel.isHelpOpen()) {
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        renderBoard();
        renderText();

        resetFontColors();
    }

    private void renderBoard() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        float introX = panelX + 84f;
        float introY = panelY + PANEL_HEIGHT - 205f;
        float introWidth = PANEL_WIDTH - 168f;

        float leftCardX = panelX + 72f;
        float rightCardX = leftCardX + CARD_WIDTH + CARD_GAP;
        float cardY = panelY + 168f;

        float footerX = panelX + 190f;
        float footerY = panelY + 74f;
        float footerWidth = PANEL_WIDTH - 380f;

        beginShapes();

        shapeRenderer.setColor(SCREEN_OVERLAY);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);

        drawRoundedRect(panelX + 14f, panelY - 14f, PANEL_WIDTH, PANEL_HEIGHT, CORNER_RADIUS, SHADOW);

        endShapes();

        renderWoodTexture(panelX, panelY);

        beginShapes();

        drawRoundedRect(panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT, CORNER_RADIUS, GOLD_DARK);
        drawRoundedRect(panelX + 7f, panelY + 7f, PANEL_WIDTH - 14f, PANEL_HEIGHT - 14f, CORNER_RADIUS - 4f, WOOD_DARK_OVERLAY);

        drawRoundedRect(introX, introY, introWidth, 96f, CARD_RADIUS, GREEN_DARK);

        renderPaperCard(leftCardX, cardY, CARD_WIDTH, CARD_HEIGHT);
        renderPaperCard(rightCardX, cardY, CARD_WIDTH, CARD_HEIGHT);

        drawRoundedRect(footerX, footerY, footerWidth, 54f, 18f, GREEN_DARK);

        renderSmallGoldOrnaments(panelX, panelY);

        endShapes();

    }

    private void renderWoodTexture(float panelX, float panelY) {
        Texture woodTexture = assetProvider.getHelpWoodTexture();

        batch.begin();

        batch.setColor(WOOD_TINT);
        batch.draw(woodTexture, panelX, panelY, PANEL_WIDTH, PANEL_HEIGHT);

        batch.setColor(Color.WHITE);

        batch.end();
    }

    private void renderPaperCard(float x, float y, float width, float height) {
        drawRoundedRect(x + 8f, y - 8f, width, height, CARD_RADIUS, SHADOW);

        drawRoundedRect(x, y, width, height, CARD_RADIUS, PAPER_DARK);
        drawRoundedRect(x + 6f, y + 6f, width - 12f, height - 12f, CARD_RADIUS - 4f, PAPER);
        drawRoundedRect(x + 18f, y + 18f, width - 36f, height - 36f, CARD_RADIUS - 8f, PAPER_INNER);

        shapeRenderer.setColor(GOLD_DARK);
        shapeRenderer.rect(x + 46f, y + height - 72f, width - 92f, 4f);
    }

    private void renderSmallGoldOrnaments(float panelX, float panelY) {
        float centerX = panelX + PANEL_WIDTH / 2f;
        float y = panelY + PANEL_HEIGHT - 122f;

        shapeRenderer.setColor(GOLD_DARK);
        shapeRenderer.rect(centerX - 250f, y, 500f, 4f);

        shapeRenderer.setColor(GOLD);
        shapeRenderer.circle(centerX - 270f, y + 2f, 7f);
        shapeRenderer.circle(centerX + 270f, y + 2f, 7f);
        shapeRenderer.circle(centerX, y + 2f, 9f);
    }

    private void renderText() {
        float panelX = getPanelX();
        float panelY = getPanelY();

        float introX = panelX + 84f;
        float introY = panelY + PANEL_HEIGHT - 205f;
        float introWidth = PANEL_WIDTH - 168f;

        float leftCardX = panelX + 72f;
        float rightCardX = leftCardX + CARD_WIDTH + CARD_GAP;
        float cardY = panelY + 168f;

        float footerX = panelX + 190f;
        float footerY = panelY + 74f;
        float footerWidth = PANEL_WIDTH - 380f;

        batch.begin();

        titleFont.setColor(TEXT_LIGHT);
        drawCentered(titleFont, "HOW TO PLAY", panelX, panelY + PANEL_HEIGHT - 56f, PANEL_WIDTH);

        textFont.setColor(TEXT_LIGHT);
        drawCentered(textFont, "Grow crops, complete quests and unlock new levels.", introX, introY + 62f, introWidth);

        textFont.setColor(new Color(0.80f, 0.88f, 0.72f, 1f));
        drawCentered(textFont, "Plant seeds, water them, harvest crops and move through the story.", introX, introY + 34f, introWidth);

        sectionFont.setColor(TEXT_DARK);
        drawCentered(sectionFont, "FARMING", leftCardX, cardY + CARD_HEIGHT - 36f, CARD_WIDTH);
        drawCentered(sectionFont, "QUESTS & MENUS", rightCardX, cardY + CARD_HEIGHT - 36f, CARD_WIDTH);

        drawPaperRow(leftCardX, cardY + 178f, "ARROWS", "Move around the farm");
        drawPaperRow(leftCardX, cardY + 132f, "P", "Plant selected seed");
        drawPaperRow(leftCardX, cardY + 86f, "W", "Water planted crop");
        drawPaperRow(leftCardX, cardY + 40f, "C", "Collect grown crop");

        drawPaperRow(rightCardX, cardY + 178f, "I", "Open or close inventory");
        drawPaperRow(rightCardX, cardY + 132f, "E", "Complete side quest");
        drawPaperRow(rightCardX, cardY + 86f, "M", "Complete main quest");
        drawPaperRow(rightCardX, cardY + 40f, "O", "Open settings");

        textFont.setColor(TEXT_LIGHT);
        drawCentered(textFont, "H - open or close help    ESC - close inventory or shop", footerX, footerY + 35f, footerWidth);

        textFont.setColor(GOLD);
        drawCentered(textFont, "Tip: crops appear after planting, but they grow only after watering.", panelX, panelY + 34f, PANEL_WIDTH);

        batch.end();
    }

    private void drawPaperRow(float cardX, float y, String key, String description) {
        float keyX = cardX + 52f;
        float descriptionX = cardX + 155f;

        textFont.setColor(GOLD_DARK);
        textFont.draw(batch, key, keyX, y + 22f);

        textFont.setColor(TEXT_DARK);
        textFont.draw(batch, description, descriptionX, y + 22f);
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

    private float getPanelX() {
        return (camera.viewportWidth - PANEL_WIDTH) / 2f;
    }

    private float getPanelY() {
        return (camera.viewportHeight - PANEL_HEIGHT) / 2f;
    }

    private void resetFontColors() {
        titleFont.setColor(Color.WHITE);
        sectionFont.setColor(Color.WHITE);
        textFont.setColor(Color.WHITE);
    }
}