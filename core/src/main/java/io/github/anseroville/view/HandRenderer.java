package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.enums.ItemType;

public class HandRenderer {
    private static final int TILE_SIZE = 75;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Texture carrot;
    private final Texture carrotSeed;

    public HandRenderer() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.carrot = new Texture("marchewka.png");
        this.carrotSeed = new Texture("nasiona_marchewek.png");
    }
    public void render(InventoryViewState state, boolean isInventoryOpen){
        if (isInventoryOpen) {
            return;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0.9f, 0.9f, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();
        batch.begin();
        if(state.getHeldItemType()==ItemType.CARROT){
            batch.draw(carrot, 1, 1, TILE_SIZE, TILE_SIZE);
        }
        else if(state.getHeldItemType()==ItemType.CARROT_SEED){
            batch.draw(carrotSeed, 1, 1, TILE_SIZE, TILE_SIZE);
        } //todo reszta jak juz beda grafiki
        batch.end();
    }
    public void dispose() {
        batch.dispose();
    }
}
