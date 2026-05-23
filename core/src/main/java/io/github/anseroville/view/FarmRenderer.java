package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.PlayerViewState;
import io.github.anseroville.viewModel.TileViewState;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.github.anseroville.viewModel.NightViewState;
import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;

public class FarmRenderer {
    private static final int TILE_SIZE = 75; //integrate tilesizes
    private static final int PLAYER_SIZE = 20;

    private final FarmViewModel viewModel;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Texture backgroundTexture;
    private final Texture pole;
    private final Texture pole_zaznaczone;
    private final Map<ItemType, Map<GrowingState, Texture>> plantTextures;

    private final Texture darknessTexture;
    private final BitmapFont font;
    private final GlyphLayout glyphLayout;

    public FarmRenderer(FarmViewModel viewModel) {
        this.viewModel = viewModel;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.backgroundTexture = new Texture("tlo.png");
        this.pole = new Texture("pole.png");
        this.pole_zaznaczone = new Texture("pole_zaznaczone.png");
        this.plantTextures = new EnumMap<>(ItemType.class);
        loadAllPlants();


        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        this.darknessTexture = new Texture(pixmap);
        pixmap.dispose();

        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.glyphLayout = new GlyphLayout();
    }

    public void render() {
        renderBackground();
        renderTiles();
        renderPlayer();
        renderNightOverlay();
    }
    private void loadAllPlants() {
        registerPlant(ItemType.CARROT, "marchewki_0.png", "marchewki_1.png", "marchewki_3.png");
        registerPlant(ItemType.CORN, "corn_0.png", "corn_1.png", "corn_3.png");
        registerPlant(ItemType.WHEAT, "wheat_0.png", "wheat_1.png", "wheat_3.png");
        registerPlant(ItemType.POTATO, "potato_0.png", "potato_1.png", "potato_3.png");}
    //z jakiegoś powodu wheat jest niższy niż powinien, zamknę ten problem później


    private void registerPlant(ItemType type, String smallPath, String mediumPath, String largePath) {
        Map<GrowingState, Texture> stateMap = new EnumMap<>(GrowingState.class);
        stateMap.put(GrowingState.SMALL, new Texture(smallPath));
        stateMap.put(GrowingState.MEDIUM, new Texture(mediumPath));
        stateMap.put(GrowingState.LARGE, new Texture(largePath));

        plantTextures.put(type, stateMap);
    }

    private void renderBackground() {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void renderTiles() {
        batch.begin();

        for (TileViewState tile : viewModel.getTileViewStates()) {
            if (tile.isSelected()) {
                batch.draw(pole_zaznaczone, tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            } else {
                batch.draw(pole, tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            }

            ItemType plantType = tile.whatGrows();
            GrowingState state = tile.getGrowingState();

            if (plantType!= null && state != null && state != GrowingState.ZERO){
                Texture plantTexture = plantTextures.get(plantType).get(state);
                batch.draw(plantTexture, tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            }
        }
        batch.end();
    }

    private void renderPlayer() {
        PlayerViewState player = viewModel.getPlayerViewState();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();
    }

    private void renderNightOverlay() {
        NightViewState nightViewState = viewModel.getNightViewState();

        if (!nightViewState.isNight()) {
            return;
        }

        if (nightViewState.hasTorch()) {
            renderTorchNightOverlay();
        } else {
            renderFullNightOverlay(nightViewState);
        }
    }

    private void renderFullNightOverlay(NightViewState nightViewState) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        batch.begin();

        batch.setColor(0f, 0f, 0f, 1f);
        batch.draw(darknessTexture, 0, 0, screenWidth, screenHeight);

        batch.setColor(Color.WHITE);

        String mainText = "Jest noc. Nie kupiles lampy!";
        glyphLayout.setText(font, mainText);

        float mainTextX = (screenWidth - glyphLayout.width) / 2f;
        float mainTextY = screenHeight / 2f + 40f;

        font.draw(batch, glyphLayout, mainTextX, mainTextY);

        String timerText = "Poczekaj " + (int) Math.ceil(nightViewState.getRemainingTime()) + " s";
        glyphLayout.setText(font, timerText);

        float timerTextX = (screenWidth - glyphLayout.width) / 2f;
        float timerTextY = screenHeight / 2f - 10f;

        font.draw(batch, glyphLayout, timerTextX, timerTextY);

        batch.setColor(Color.WHITE);
        batch.end();
    }

    private void renderTorchNightOverlay() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        int edgeSize = 120;

        batch.begin();

        batch.setColor(0f, 0f, 0f, 0.8f);

        batch.draw(darknessTexture, 0, 0, screenWidth, edgeSize);
        batch.draw(darknessTexture, 0, screenHeight - edgeSize, screenWidth, edgeSize);
        batch.draw(darknessTexture, 0, 0, edgeSize, screenHeight);
        batch.draw(darknessTexture, screenWidth - edgeSize, 0, edgeSize, screenHeight);

        batch.setColor(Color.WHITE);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
        pole.dispose();
        pole_zaznaczone.dispose();

        darknessTexture.dispose();
        font.dispose();

        for (Map<GrowingState, Texture> stateMap : plantTextures.values()) {
            for (Texture texture : stateMap.values()) {
                texture.dispose();
            }
        }
    }
}