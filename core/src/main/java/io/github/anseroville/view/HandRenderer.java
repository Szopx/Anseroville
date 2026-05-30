package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.enums.ItemType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.Map;
import java.util.EnumMap;

public class HandRenderer {
    private static final int TILE_SIZE = 75;

    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Map<ItemType, Texture> itemTextures;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    public HandRenderer(OrthographicCamera camera) {
        this.batch = new SpriteBatch();
        this.camera=camera;
        this.shapeRenderer = new ShapeRenderer();
        this.itemTextures = new EnumMap<>(ItemType.class);
        this.font=new BitmapFont();
        this.font.getData().setScale(1.0f);
        loadItemTextures();
    }

    private void loadItemTextures() {
        itemTextures.put(ItemType.CARROT, new Texture("marchewka.png"));
        itemTextures.put(ItemType.CORN, new Texture("corn.png"));
        itemTextures.put(ItemType.WHEAT, new Texture("wheat.png"));
        itemTextures.put(ItemType.POTATO, new Texture("potato.png"));
        itemTextures.put(ItemType.CARROT_SEED, new Texture("nasiona_marchewek.png"));
        itemTextures.put(ItemType.CORN_SEED, new Texture("corn_seed.png"));
        itemTextures.put(ItemType.WHEAT_SEED, new Texture("wheat_seeds.png"));
        itemTextures.put(ItemType.POTATO_SEED, new Texture("potato_seed.png"));
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
            Texture texture = itemTextures.get(state.getHeldItemType());

            if (texture != null) {
                batch.begin();
                batch.draw(texture, width, height, TILE_SIZE, TILE_SIZE);
                font.draw(batch, "x"+state.getHeldItemAmount(), width+TILE_SIZE-25, height+15);
                batch.end();
            }
        }
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        for (Texture texture : itemTextures.values()) {
            texture.dispose();
        }
    }
}