package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.PlayerViewState;
import io.github.anseroville.viewModel.TileViewState;

public class FarmRenderer {
    private static final int TILE_SIZE = 65; //integrate tilesizes
    private static final int PLAYER_SIZE = 20;

    private final FarmViewModel viewModel;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Texture backgroundTexture;

    public FarmRenderer(FarmViewModel viewModel) {
        this.viewModel = viewModel;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.backgroundTexture = new Texture("tlo.png");
    }

    public void render() {
        renderBackground();
        renderTiles();
        renderPlayer();

    }

    private void renderBackground() {
        batch.begin();
        batch.draw(backgroundTexture, -60, -40);
        batch.end();
    }

    private void renderTiles() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (TileViewState tile : viewModel.getTileViewStates()) {
            if (tile.isSelected()) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(Color.WHITE);
            }

            shapeRenderer.rect(tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
        }

        shapeRenderer.end();
    }

    private void renderPlayer() {
        PlayerViewState player = viewModel.getPlayerViewState();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
    }
}