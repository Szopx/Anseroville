package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.viewModel.FarmViewModel;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.anseroville.viewModel.QuestViewState;

public class StatusBarRenderer {
    private final SpriteBatch batch;
    private final AssetProvider assetProvider;
    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final BitmapFont mediumFont;
    private final BitmapFont bigFont;

    public StatusBarRenderer(FarmViewModel viewModel,
                             OrthographicCamera camera,
                             AssetProvider assetProvider,
                             SpriteBatch batch) {
        this.batch=batch;
        this.camera=camera;
        this.assetProvider=assetProvider;
        this.viewModel=viewModel;
        this.mediumFont =assetProvider.getSmallFont();
        this.bigFont=assetProvider.getBigFont();
    }

    public void render(){
        QuestViewState state=viewModel.getQuestViewState();
        batch.setProjectionMatrix(camera.combined);
        int rectangleHeight=80;
        float startY=camera.viewportHeight-rectangleHeight;
        float width=camera.viewportWidth;
        batch.begin();
        batch.setColor(0f, 0f, 0f, 0.8f);
        batch.draw(assetProvider.getDarknessTexture(), 0, startY, width, rectangleHeight);
        int moneyAmount=viewModel.getMoney();
        float startX=camera.viewportWidth-250;
        batch.setColor(Color.WHITE);
        batch.draw(assetProvider.getCoinTexture(), startX, startY+5, 70f, 70f);
        bigFont.draw(batch, String.valueOf(moneyAmount), startX+70, startY+52);
        mediumFont.draw(batch, "H - Help    U - Interact", 25, startY + 47.5f);
        float levelStartX=camera.viewportWidth/2-75;
        bigFont.draw(batch, "LEVEL " + state.getActiveLevelNumber() + "/" + state.getMaxLevelNumber(), levelStartX, startY+50f);
        batch.end();
    }
}
