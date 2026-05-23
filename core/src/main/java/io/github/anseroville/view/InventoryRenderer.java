package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.InventoryViewState;
import java.util.Map;
import java.util.HashMap;

public class InventoryRenderer {
    private static final int ICON_SIZE = 90;
    private final SpriteBatch batch;
    private final Texture carrot;
    private final Texture carrotSeed;
    private final Texture inventory;
    private final BitmapFont font;
    private final Map <ItemType, Texture> itemTextures;


    public InventoryRenderer(){
        this.batch=new SpriteBatch();
        this.inventory = new Texture("1779347732838.png");
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.carrot = new Texture("marchewka.png");
        this.carrotSeed = new Texture("nasiona_marchewek.png");
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
        batch.begin();
        batch.draw(inventory, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Ustawienia układu ekwipunku
        float startX = Gdx.graphics.getWidth() * 0.12f;
        float startY = Gdx.graphics.getHeight() * 0.73f;
        float offsetX = 160f;
        float offsetY = 165f;
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

        }}
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
