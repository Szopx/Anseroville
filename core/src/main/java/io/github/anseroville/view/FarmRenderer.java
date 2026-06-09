package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.PlayerViewState;
import io.github.anseroville.viewModel.TileViewState;
import io.github.anseroville.enums.Direction;

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
        this.assetProvider = assetProvider;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
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
        if (viewModel.getLevelNumber() ==2){ batch.draw(assetProvider.getBackgroundTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==1){ batch.draw(assetProvider.springBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==4){ batch.draw(assetProvider.winterBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else { batch.draw(assetProvider.autumnBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        batch.end();
    }

    private void renderTiles() {
        batch.begin();

        for (TileViewState tile : viewModel.getTileViewStates()) {
            ActivityTileType activityHere = tile.getActivityTileType();
            if (activityHere != null) {

                switch (activityHere) {
                    case SHOP:
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.getSelectedShopTexture(), tile.getX() - 560 / 2 + 50, tile.getY(), 560, 400);

                        } else {
                            batch.draw(assetProvider.getShopTexture(), tile.getX() - 560 / 2 + 50, tile.getY(), 560, 400);
                        }
                        break;
                    case GAMBLING:
                        batch.draw(assetProvider.getGamblingTexture(), tile.getX() - 15, tile.getY(), 150, 200);
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.gamblingTextureSelected, tile.getX()-15, tile.getY(), 150, 200);

                        }
                        break;
                    case MAIN_QUEST:

                        //todo z jakiegoś powodu zwierzęta na wyższych levelach zaczynają z selected
                        int c = 1;
                        Texture texture = assetProvider.getGeeseTexture();
                        Texture selectedTexture = assetProvider.getGeeseTextureSelected();
                        if (viewModel.getLevelNumber() == 1) {
                            c = 3;
                            texture = assetProvider.getChickenTexture();
                            selectedTexture = assetProvider.getChickenTextureSelected();
                        } else if (viewModel.getLevelNumber() == 2) {
                            c = 3;
                            texture = assetProvider.getHedgehogTexture();
                            selectedTexture = assetProvider.getHedgehogTextureSelected();
                        } else if (viewModel.getLevelNumber() == 3) {
                            c = 3;
                            texture = assetProvider.getFrogTexture();
                            selectedTexture = assetProvider.getFrogTextureSelected();
                        } else if (viewModel.getLevelNumber() == 4) {
                            c = 3;
                            texture = assetProvider.getSheepTexture();
                            selectedTexture = assetProvider.getSheepTextureSelected();
                        }
                        if (tile.isSelected()) {
                            batch.draw(selectedTexture, tile.getX(), tile.getY(), texture.getWidth() * c, texture.getHeight() * c);
                        } else {
                            batch.draw(texture, tile.getX(), tile.getY(), texture.getWidth() * c, texture.getHeight() * c);
                        }
                        break;
                    case QUEST:
                        c = 3;
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.getGeeseTextureSelected(), tile.getX(), tile.getY(), assetProvider.getGeeseTextureSelected().getWidth() * c, assetProvider.getGeeseTextureSelected().getHeight() * c);

                        } else {
                            batch.draw(assetProvider.getGeeseTexture(), tile.getX(), tile.getY(), assetProvider.getGeeseTextureSelected().getWidth() * c, assetProvider.getGeeseTextureSelected().getHeight() * c);
                        }
                        break;
                    case WATER:
                        c = 3;
                        int offset=140;
                        int offsety=50;
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.getBridgeTextureSelected(), tile.getX()-offset, tile.getY()-offsety, assetProvider.getBridgeTextureSelected().getWidth() * c, assetProvider.getBridgeTextureSelected().getHeight() * c);

                        } else {
                            batch.draw(assetProvider.getBridgeTexture(), tile.getX()-offset, tile.getY()-offsety, assetProvider.getBridgeTextureSelected().getWidth() * c, assetProvider.getBridgeTextureSelected().getHeight() * c);
                        }
                        break;
                }

            } else {

                if (tile.isWatered()) {
                    batch.draw(assetProvider.getWateredFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(assetProvider.getFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                }
                if (tile.isSelected()) { //todo inny selected dla podlanego musi byc
                    batch.draw(assetProvider.getSelectedFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                }

                ItemType plantType = tile.whatGrows();
                GrowingState state = tile.getGrowingState();

                if (plantType != null && state != null && state != GrowingState.ZERO) {
                    Texture plantTexture = assetProvider.getPlantTexture(plantType, state);

                    if (plantTexture != null) {
                        batch.draw(plantTexture, tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
        batch.end();
    }

    private void renderPlayer() {
        Direction direction = viewModel.getPlayerViewState().getDirection();
        PlayerViewState player = viewModel.getPlayerViewState();

        int c = 2;
        Texture texture = switch (direction) {
            case DOWN -> assetProvider.getPlayerFrontTexture();
            case UP -> assetProvider.getPlayerBackTexture();
            case LEFT -> assetProvider.getPlayerLeftTexture();
            case RIGHT -> assetProvider.getPlayerRightTexture();
        };


        batch.begin();
        batch.draw(texture, player.getX()-25, player.getY()-25,
                texture.getWidth() * c, texture.getHeight() * c);
        batch.end();
    }
}