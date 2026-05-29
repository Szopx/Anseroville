package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.InventoryViewState;
import java.util.Map;
import java.util.HashMap;

public class InventoryRenderer {
    private static final int ICON_SIZE = 90;
    private final SpriteBatch batch;
    private final Texture inventory;
    private final BitmapFont font;
    private final Map <ItemType, Texture> itemTextures;
    private final OrthographicCamera camera;


    public InventoryRenderer(OrthographicCamera camera){
        this.batch=new SpriteBatch();
        this.camera=camera;
        this.inventory = new Texture("1779347732838.png");
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.itemTextures = new HashMap<>();
        loadItemTextures();
    }
    void loadItemTextures(){
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
        batch.begin();
        float width=camera.viewportWidth;
        float height=camera.viewportHeight;
        batch.draw(inventory, 0, 0, width, height);

        float startX = width*0.12f;
        float startY = height*0.73f;
        float offsetX = 170f;
        float offsetY = 180f;
        int columns = 4;

        int index = 0;

        for (ItemType itemType : ItemType.values()) {

            int amount = state.getAmount(itemType);

                Texture texture = itemTextures.get(itemType);
                if (texture != null){
                int col = index % columns;
                int row = index / columns;

                float drawX = startX + (col * offsetX);
                float drawY = startY - (row * offsetY);

                batch.draw(texture, drawX, drawY, ICON_SIZE, ICON_SIZE);
                font.draw(batch, "x " + amount, drawX + 45, drawY + 15);

                index++;

            }
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        inventory.dispose();
        font.dispose();

        for (Texture texture : itemTextures.values()) {
            texture.dispose();
        }
    }
}
