package io.github.anseroville.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.ShopViewState;

public class ShopRenderer {
    private static final int ICON_SIZE = 85;
    private static final float START_X_BUY_RATIO = 0.11f;
    private static final float START_X_SELL_RATIO = 0.59f;
    private static final float START_Y_RATIO = 0.523f;
    private static final float OFFSET_X = 117f;
    private static final float OFFSET_Y = 140f;
    private static final int COLUMNS = 5;

    private final AssetProvider assetProvider;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Texture coinTexture;

    public ShopRenderer(AssetProvider assetProvider, OrthographicCamera camera, SpriteBatch batch) {
        this.assetProvider = assetProvider;
        this.camera = camera;
        this.batch = batch;
        this.font = assetProvider.getSmallFont();
        this.coinTexture = assetProvider.getCoinTexture();
    }

    public void render(ShopViewState state) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;
        float startY = height * START_Y_RATIO;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(assetProvider.getShopInsideTexture(), 0, 0, width, height);

        float startX = width * START_X_BUY_RATIO;
        int index = 0;

        for (ItemType itemType : state.getBuyPrices().keySet()) {
            int amount = state.getBuyPrices().get(itemType);
            renderItem(itemType, amount, startX, startY, index);
            index++;
        }

        startX = width * START_X_SELL_RATIO;
        index = 0;

        for (ItemType itemType : state.getSellPrices().keySet()) {
            int amount = state.getSellPrices().get(itemType); //todo praktycznie duplikat kodu z inventory
            renderItem(itemType, amount, startX, startY, index);
            index++;
        }

        batch.end();
    }
    private void renderItem(ItemType type, int amount, float startX, float startY, int index) {
        Texture texture = assetProvider.getItemTexture(type);

        if (texture != null) {
            int col = index % COLUMNS;
            int row = index / COLUMNS;

            float drawX = startX + (col * OFFSET_X);
            float drawY = startY - (row * OFFSET_Y);

            batch.draw(texture, drawX, drawY, ICON_SIZE, ICON_SIZE);

            font.draw(batch, "Price: " + amount, drawX + 5, drawY - 5);

            float coinOffsetX = (amount >= 10) ? 73f : 68f;
            batch.draw(coinTexture, drawX + coinOffsetX, drawY - 21, 20, 20);
        }
    }
}