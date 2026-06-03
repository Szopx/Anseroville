package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.InventoryViewState;

public class InventoryRenderer {
    private static final int ICON_SIZE = 90;
    private final SpriteBatch batch;
    private final AssetProvider assetProvider;
    private final BitmapFont font;
    private final OrthographicCamera camera;


    public InventoryRenderer(OrthographicCamera camera,
                             AssetProvider assetProvider,
                             SpriteBatch batch) {
        this.batch=batch;
        this.assetProvider=assetProvider;
        this.camera=camera;
        this.font=assetProvider.getBigFont();
    }

    public void render(InventoryViewState state){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float width=camera.viewportWidth;
        float height=camera.viewportHeight;
        batch.draw(assetProvider.getInventoryTexture(), 0, 0, width, height);

        float startX = width*0.12f;
        float startY = height*0.73f;
        float offsetX = 170f;
        float offsetY = 180f;
        int columns = 4;

        int index = 0;

        for (ItemType itemType : ItemType.values()) {

            int amount = state.getAmount(itemType);

                Texture texture = assetProvider.getItemTexture(itemType);
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
}
