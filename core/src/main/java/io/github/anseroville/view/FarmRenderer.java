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
        if (viewModel.getLevelNumber() ==1){ batch.draw(assetProvider.getBackgroundTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==2){ batch.draw(assetProvider.springBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==3){ batch.draw(assetProvider.winterBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
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
                        Texture textura = assetProvider.geeseTexture;
                        Texture textura_selected = assetProvider.geeseTexture_selected;
                        if (viewModel.getLevelNumber() == 1) {
                            c = 3;
                            textura = assetProvider.chickenTexture;
                            textura_selected = assetProvider.chickenTexture_selected;
                        } else if (viewModel.getLevelNumber() == 2) {
                            c = 3;
                            textura = assetProvider.hedgehogTexture;
                            textura_selected = assetProvider.hedgehogTexture_selected;
                        } else if (viewModel.getLevelNumber() == 3) {
                            c = 3;
                            textura = assetProvider.frogTexture;
                            textura_selected = assetProvider.frogTexture_selected;
                        } else if (viewModel.getLevelNumber() == 4) {
                            c = 3;
                            textura = assetProvider.sheepTexture;
                            textura_selected = assetProvider.sheepTexture_selected;
                        }
                        if (tile.isSelected()) {
                            batch.draw(textura_selected, tile.getX(), tile.getY(), textura.getWidth() * c, textura.getHeight() * c);
                        } else {
                            batch.draw(textura, tile.getX(), tile.getY(), textura.getWidth() * c, textura.getHeight() * c);
                        }
                        break;
                    case QUEST:
                        c = 3;
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.geeseTexture_selected, tile.getX(), tile.getY(), assetProvider.geeseTexture_selected.getWidth() * c, assetProvider.geeseTexture_selected.getHeight() * c);

                        } else {
                            batch.draw(assetProvider.geeseTexture, tile.getX(), tile.getY(), assetProvider.geeseTexture_selected.getWidth() * c, assetProvider.geeseTexture_selected.getHeight() * c);
                        }
                        break;
                    case WATER:
                        //todo zrobić offset
                        c = 3;
                        int offset=100;
                        if (tile.isSelected()) {
                            batch.draw(assetProvider.bridgeTexture_selected, tile.getX()-offset, tile.getY(), assetProvider.bridgeTexture_selected.getWidth() * c, assetProvider.bridgeTexture_selected.getHeight() * c);

                        } else {
                            batch.draw(assetProvider.bridgeTexture, tile.getX()-offset, tile.getY(), assetProvider.bridgeTexture_selected.getWidth() * c, assetProvider.bridgeTexture_selected.getHeight() * c);
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
        //System.out.println(viewModel.getPlayerViewState().getDirection());
        PlayerViewState player = viewModel.getPlayerViewState();
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();*/

        int c = 2;
        Texture textura = assetProvider.playerFrontTexture;
        switch (direction) {
            case DOWN:
                textura = assetProvider.playerFrontTexture;
                break;
            case UP:
                textura = assetProvider.playerBackTexture;
                break;
            case LEFT:
                textura = assetProvider.playerleftTexture;
                break;
            case RIGHT:
                textura = assetProvider.playerrightTexture;
                break;
        }


        batch.begin();
        batch.draw(textura, player.getX(), player.getY(), textura.getWidth() * c, textura.getHeight() * c);
        batch.end();
    }



}