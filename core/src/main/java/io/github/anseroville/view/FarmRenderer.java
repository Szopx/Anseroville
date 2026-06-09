package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.enums.ActivityTileType;
import io.github.anseroville.enums.Direction;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
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
    private float stateTime = 0f;
    private float playerStateTime = 0f;
    private int lastPlayerX = -999;
    private int lastPlayerY = -999;
    private float animalOffsetX = 0f;
    private int lastLevelNumber = -1;

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
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        PlayerViewState player = viewModel.getPlayerViewState();
        if (player.getX() != lastPlayerX || player.getY() != lastPlayerY) {
            playerStateTime += delta;
            lastPlayerX = player.getX();
            lastPlayerY = player.getY();
        } else {
            playerStateTime = 0f;
        }
        if (viewModel.getLevelNumber() != lastLevelNumber) {
            animalOffsetX = camera.viewportWidth;
            lastLevelNumber = viewModel.getLevelNumber();
        }

        if (animalOffsetX > 0) {
            animalOffsetX -= 200f * delta;
            if (animalOffsetX < 0) {
                animalOffsetX = 0f;
            }
        }
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        renderBackground();
        renderTiles();
        renderPlayer();
        batch.end();
    }

    private void renderBackground() {
        if (viewModel.getLevelNumber() ==2){ batch.draw(assetProvider.getBackgroundTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==1){ batch.draw(assetProvider.springBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else if (viewModel.getLevelNumber() ==4){ batch.draw(assetProvider.winterBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
        else { batch.draw(assetProvider.autumnBackgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);}
    }

    private void renderTiles() {

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
                        TextureRegion pidgeonFrame = assetProvider.getPidgeonAnim().getKeyFrame(stateTime, true);
                        batch.draw(pidgeonFrame, tile.getX() - 15, tile.getY(), 150, 200);

                        if (tile.isSelected()) {
                            batch.draw(assetProvider.gamblingTextureSelected, tile.getX() - 15, tile.getY(), 150, 200);
                        }
                        break;
                    case MAIN_QUEST:

                        //todo z jakiegoś powodu zwierzęta na wyższych levelach zaczynają z selected
                        int c = 1;
                        Animation<TextureRegion> anim;
                        anim = assetProvider.getGeeseAnim();
                        Texture selectedTexture = assetProvider.getGeeseTextureSelected();
                        if (viewModel.getLevelNumber() == 2) {
                            c = 3;
                            anim = assetProvider.getChickenAnim();
                            selectedTexture = assetProvider.getChickenTextureSelected();
                        } else if (viewModel.getLevelNumber() == 3) {
                            c = 3;
                            anim = assetProvider.getHedgehogAnim();
                            selectedTexture = assetProvider.getHedgehogTextureSelected();
                        } else if (viewModel.getLevelNumber() == 1) {
                            c = 3;
                            anim = assetProvider.getFrogAnim();
                            selectedTexture = assetProvider.getFrogTextureSelected();
                        } else if (viewModel.getLevelNumber() == 4) {
                            c = 3;
                            anim = assetProvider.getSheepAnim();
                            selectedTexture = assetProvider.getSheepTextureSelected();
                        }
                        if (tile.isSelected()) {
                            batch.draw(selectedTexture, tile.getX(), tile.getY(), selectedTexture.getWidth() * c, selectedTexture.getHeight() * c);
                        } else {
                            TextureRegion frame = anim.getKeyFrame(stateTime, true);
                            batch.draw(frame, tile.getX(), tile.getY(), frame.getRegionWidth() * c, frame.getRegionHeight() * c);;
                        }
                        break;
                    case QUEST:
                        c = 3;
                        if (tile.isSelected()) {
                            Texture sel = assetProvider.getGeeseTextureSelected();
                            batch.draw(sel, tile.getX() + animalOffsetX, tile.getY(), sel.getWidth() * c, sel.getHeight() * c);
                        } else {
                            float timeToUse = (animalOffsetX > 0) ? stateTime : 0f;
                            TextureRegion frame = assetProvider.getGeeseAnim().getKeyFrame(timeToUse, true);
                            batch.draw(frame, tile.getX() + animalOffsetX, tile.getY(), frame.getRegionWidth() * c, frame.getRegionHeight() * c);
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
                if (tile.isSelected()) {
                    batch.draw(assetProvider.getSelectedFieldTexture(), tile.getX(), tile.getY(), TILE_SIZE, TILE_SIZE);
                }


            }
        }

        for (TileViewState tile : viewModel.getTileViewStates())
            {ItemType plantType = tile.whatGrows();
            GrowingState state = tile.getGrowingState();

            if (plantType != null && state != null && state != GrowingState.ZERO) {
                Texture plantTexture = assetProvider.getPlantTexture(plantType, state);
                int sizey= TILE_SIZE;
                if (plantType!= ItemType.POTATO && plantType!= ItemType.CARROT) {sizey*=2;}
                if (plantTexture != null) {
                    batch.draw(plantTexture, tile.getX(), tile.getY(), TILE_SIZE, sizey);
                }
            }
        }
    }

    private void renderPlayer() {
        Direction direction = viewModel.getPlayerViewState().getDirection();
        PlayerViewState player = viewModel.getPlayerViewState();
        Animation<TextureRegion> anim;
        int c = 2;
        anim = switch (direction) {
            case DOWN -> assetProvider.getPlayerFrontAnim();
            case UP -> assetProvider.getPlayerBackAnim();
            case LEFT -> assetProvider.getPlayerLeftAnim();
            case RIGHT -> assetProvider.getPlayerRightAnim();
        };
        TextureRegion frame = anim.getKeyFrame(playerStateTime, true);
        batch.draw(frame, player.getX()-25, player.getY()-25, frame.getRegionWidth() * c, frame.getRegionHeight() * c);
    }
}