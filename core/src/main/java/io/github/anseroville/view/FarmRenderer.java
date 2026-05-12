package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.model.Tiles.GrowingState;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.PlayerViewState;
import io.github.anseroville.viewModel.TileViewState;

public class FarmRenderer {
    private static final int TILE_SIZE = 75; //integrate tilesizes
    private static final int PLAYER_SIZE = 20;

    private final FarmViewModel viewModel;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Texture backgroundTexture;
    private final Texture pole;
    private final Texture pole_zaznaczone;
    private final Texture[] marchewki;

    public FarmRenderer(FarmViewModel viewModel) {
        this.viewModel = viewModel;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.backgroundTexture = new Texture("tlo.png");
        this.pole = new Texture("pole.png");
        this.pole_zaznaczone = new Texture("pole_zaznaczone.png");
        this.marchewki = new Texture[3];
        this.marchewki[0] = new Texture("marchewki_0.png");
        this.marchewki[1] = new Texture("marchewki_1.png");
        this.marchewki[2] = new Texture("marchewki_3.png");
    }

    public void render() {
        renderBackground();
        renderTiles();
        renderPlayer();

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
            if (tile.getGrowingState()== GrowingState.SMALL)
            {batch.draw(marchewki[0], tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);}
            else if(tile.getGrowingState()== GrowingState.MEDIUM)
            {batch.draw(marchewki[1], tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);}
            else if(tile.getGrowingState()==GrowingState.LARGE)
            {batch.draw(marchewki[2], tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);}
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

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
        pole.dispose();
        pole_zaznaczone.dispose();
    }
}