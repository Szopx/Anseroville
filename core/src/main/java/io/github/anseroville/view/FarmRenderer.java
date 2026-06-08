package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.PlayerViewState;
import io.github.anseroville.viewModel.TileViewState;

public class FarmRenderer {
    private static final int TILE_SIZE = 75; //integrate tilesizes
    private static final int PLAYER_SIZE = 20;

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final AssetProvider assetProvider;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    public FarmRenderer(FarmViewModel viewModel,
                        OrthographicCamera camera,
                        AssetProvider assetProvider,
                        SpriteBatch batch,
                        ShapeRenderer shapeRenderer) {
        this.viewModel = viewModel;
        this.camera = camera;
        this.assetProvider=assetProvider;
        this.batch=batch;
        this.shapeRenderer=shapeRenderer;
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        renderBackground();
        renderTiles();
        renderPlayer();
    }

    private void renderBackground() {
        batch.begin();
        batch.draw(assetProvider.getBackgroundTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.end();
    }

    private void renderTiles() {
        batch.begin();

        for (TileViewState tile : viewModel.getTileViewStates()) {
            ActivityTileType activityHere = tile.getActivityTileType();
            if (activityHere!=null){

                switch (activityHere) {
                    case SHOP:
                        batch.draw(assetProvider.getShopTexture(), tile.getX()-560/2+30, tile.getY(), 560, 400);
                        break;
                    case GAMBLING:
                        batch.draw(assetProvider.getGamblingTexture(), tile.getX()-30, tile.getY(), 150, 200);
                }
                if (tile.isSelected()){
                    batch.draw(assetProvider.getSelectedFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);

                }
            }
            else{
            if (tile.isSelected()) { //todo inny selected dla podlanego musi byc
                batch.draw(assetProvider.getSelectedFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            } else if (tile.isWatered()){
                batch.draw(assetProvider.getWateredFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            } else {
                batch.draw(assetProvider.getFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
            }

            ItemType plantType = tile.whatGrows();
            GrowingState state = tile.getGrowingState();

            if (plantType != null && state != null && state != GrowingState.ZERO) {
                Texture plantTexture = assetProvider.getPlantTexture(plantType, state);

                if (plantTexture != null) {
                    batch.draw(plantTexture, tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                }
            }
        }}
        batch.end();
    }

    private void renderPlayer() {
        PlayerViewState player = viewModel.getPlayerViewState();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();
    }
}