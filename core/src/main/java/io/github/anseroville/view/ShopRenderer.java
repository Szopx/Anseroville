package io.github.anseroville.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.ShopViewState;

public class ShopRenderer {
    private static final int ICON_SIZE = 85;
    private final AssetProvider assetProvider;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public ShopRenderer(AssetProvider assetProvider,
                        OrthographicCamera camera,
                        SpriteBatch batch) {
        this.assetProvider = assetProvider;
        this.camera = camera;
        this.batch = batch;
        this.font = assetProvider.getMediumFont();
    }

    public void render(ShopViewState state){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;
        batch.draw(assetProvider.getShopInsideTexture(), 0, 0, width, height);

        float startX = width * 0.1f;
        float startY = height * 0.545f;

        float offsetX = 127f;
        float offsetY = 140f;

        int columns = 5;
        int index = 0;

        for(ItemType itemType : state.getBuyPrices().keySet()){
            int amount = state.getBuyPrices().get(itemType);
            Texture texture = assetProvider.getItemTexture(itemType);

            if (texture != null){
                int col = index % columns;
                int row = index / columns;

                float drawX = startX + (col * offsetX);
                float drawY = startY - (row * offsetY);

                batch.draw(texture, drawX, drawY, ICON_SIZE, ICON_SIZE);

                font.draw(batch, String.valueOf(amount), drawX + 65, drawY + 15);

                index++;
            }
        }
        startX = width * 0.59f;
        index = 0;
        offsetX = 135f;
        for(ItemType itemType : state.getSellPrices().keySet()){
            int amount = state.getSellPrices().get(itemType);
            Texture texture = assetProvider.getItemTexture(itemType);

            if (texture != null){
                int col = index % columns;
                int row = index / columns;

                float drawX = startX + (col * offsetX);
                float drawY = startY - (row * offsetY);

                batch.draw(texture, drawX, drawY, ICON_SIZE, ICON_SIZE);

                font.draw(batch, String.valueOf(amount), drawX + 65, drawY + 15);

                index++;
            }
        }

        batch.end();
    }
}