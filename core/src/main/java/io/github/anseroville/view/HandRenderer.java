package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.viewModel.InventoryViewState;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HandRenderer {
    private static final int TILE_SIZE = 75;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private final AssetProvider assetProvider;

    public HandRenderer(OrthographicCamera camera,
                        SpriteBatch batch,
                        ShapeRenderer shapeRenderer,
                        AssetProvider assetProvider) {
        this.batch = batch;
        this.camera=camera;
        this.shapeRenderer = shapeRenderer;
        this.assetProvider=assetProvider;
        this.font=assetProvider.getSmallestFont();
    }


    public void render(InventoryViewState state){
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        float height=camera.viewportHeight-TILE_SIZE-2.5f;
        float width=camera.viewportWidth-80;
        shapeRenderer.rect(width, height, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();

        if (state.hasItemInHand()) {
            Texture texture = assetProvider.getItemTexture(state.getHeldItemType());

            if (texture != null) {
                batch.begin();
                batch.draw(texture, width, height, TILE_SIZE, TILE_SIZE);
                font.draw(batch, "x"+state.getHeldItemAmount(), width+TILE_SIZE-25, height+15);
                batch.end();
            }
        }
    }
}