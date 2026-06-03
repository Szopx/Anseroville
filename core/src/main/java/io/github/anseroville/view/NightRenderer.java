package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.NightViewState;

public class NightRenderer {

    private final FarmViewModel viewModel;
    private final GlyphLayout glyphLayout;
    private final OrthographicCamera camera;
    private final AssetProvider assetProvider;
    private final BitmapFont font;
    private final SpriteBatch batch;

    public NightRenderer(FarmViewModel viewModel,
                         OrthographicCamera camera,
                         AssetProvider assetProvider,
                         SpriteBatch batch) {
        this.viewModel=viewModel;
        this.glyphLayout = new GlyphLayout();
        this.camera = camera;
        this.assetProvider = assetProvider;
        this.font=assetProvider.getBigFont();
        this.batch=batch;
    }

    public void render() {
        NightViewState nightViewState = viewModel.getNightViewState();

        if (!nightViewState.isNight()) {
            return;
        }

        batch.setProjectionMatrix(camera.combined);

        if (nightViewState.hasTorch()) {
            renderTorchNightOverlay();
        } else {
            renderFullNightOverlay(nightViewState);
        }
    }

    private void renderFullNightOverlay(NightViewState nightViewState) {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        batch.begin();

        batch.setColor(0f, 0f, 0f, 1f);
        batch.draw(assetProvider.getDarknessTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight);

        batch.setColor(Color.WHITE);

        String mainText = "Jest noc. Nie kupiles lampy!";
        glyphLayout.setText(font, mainText);

        float mainTextX = (screenWidth - glyphLayout.width) / 2f;
        float mainTextY = screenHeight / 2f + 40f;

        font.draw(batch, glyphLayout, mainTextX, mainTextY);

        String timerText = "Poczekaj " + (int) Math.ceil(nightViewState.getRemainingTime()) + " s";
        glyphLayout.setText(font, timerText);

        float timerTextX = (screenWidth - glyphLayout.width) / 2f;
        float timerTextY = screenHeight / 2f - 10f;

        font.draw(batch, glyphLayout, timerTextX, timerTextY);

        batch.setColor(Color.WHITE);
        batch.end();
    }

    private void renderTorchNightOverlay() {
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;
        Texture darknessTexture=assetProvider.getDarknessTexture();

        int edgeSize = 120;

        batch.begin();

        batch.setColor(0f, 0f, 0f, 0.8f);

        batch.draw(darknessTexture, 0, 0, screenWidth, edgeSize);
        batch.draw(darknessTexture, 0, screenHeight - edgeSize, screenWidth, edgeSize);
        batch.draw(darknessTexture, 0, 0, edgeSize, screenHeight);
        batch.draw(darknessTexture, screenWidth - edgeSize, 0, edgeSize, screenHeight);

        batch.setColor(Color.WHITE);
        batch.end();
    }
}
