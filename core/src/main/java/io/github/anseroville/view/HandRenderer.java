package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.enums.ItemType;
import java.util.Map;
import java.util.EnumMap;

public class HandRenderer {
    private static final int TILE_SIZE = 75;

    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Map<ItemType, Texture> itemTextures;

    public HandRenderer() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.itemTextures = new EnumMap<>(ItemType.class);
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

    public void render(InventoryViewState state, boolean isInventoryOpen){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0.9f, 0.9f, TILE_SIZE, TILE_SIZE);
        shapeRenderer.end();

        if (state.hasItemInHand()) {
            Texture texture = itemTextures.get(state.getHeldItemType());

            if (texture != null) {
                batch.begin();
                batch.draw(texture, 1, 1, TILE_SIZE, TILE_SIZE);
                batch.end();
            }
        }
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        for (Texture texture : itemTextures.values()) {
            texture.dispose();
        }
    }
}