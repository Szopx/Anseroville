package io.github.anseroville.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.NightViewState;

public class NightRenderer {

    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;
    private final AssetProvider assetProvider;
    private final SpriteBatch batch;

    public NightRenderer(FarmViewModel viewModel,
                         OrthographicCamera camera,
                         AssetProvider assetProvider,
                         SpriteBatch batch) {
        this.viewModel = viewModel;
        this.camera = camera;
        this.assetProvider = assetProvider;
        this.batch = batch;
    }

    public void render() {
        NightViewState nightViewState = viewModel.getNightViewState();

        if (!nightViewState.isNight()) {
            return;
        }

        batch.begin(); // todo: przesunac to wszystko w jedno miejsce
        batch.setProjectionMatrix(camera.combined);

        if (nightViewState.hasTorch()) {
            batch.draw(assetProvider.getNightWithLampTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);
        } else {
            batch.draw(assetProvider.getNightWithoutLampTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);

            int secondsLeft = (int) Math.ceil(nightViewState.getRemainingTime());

            String countdownText = "You didn't buy a lantern!\nNighttime remaining: " + secondsLeft + "s";

            BitmapFont bigFont = assetProvider.getBigFont();

            float centerX = camera.viewportWidth / 2f;
            float centerY = camera.viewportHeight / 2f;

            float adjustedY = centerY + (bigFont.getCapHeight() / 2f);

            bigFont.draw(batch, countdownText, centerX, adjustedY, 0, Align.center, false);
        }

        batch.end();
    }
}