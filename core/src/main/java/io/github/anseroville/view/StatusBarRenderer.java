package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.viewModel.FarmViewModel;

public class StatusBarRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final FarmViewModel viewModel;
    private final Texture money;
    private final Texture statusBarTexture;

    public StatusBarRenderer(FarmViewModel viewModel) {
        this.batch=new SpriteBatch();
        this.font=new BitmapFont();
        this.font.getData().setScale(2f);
        this.viewModel=viewModel;
        this.money=new Texture("coin.png");
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        this.statusBarTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public void render(){
        int rectangleHeight=80;
        float startY=Gdx.graphics.getHeight()-rectangleHeight;
        float width=Gdx.graphics.getWidth();
        batch.begin();
        batch.setColor(0f, 0f, 0f, 0.8f);
        batch.draw(statusBarTexture, 0, startY, width, rectangleHeight);
        int moneyAmount=viewModel.getMoney();
        float startX=Gdx.graphics.getWidth()-250;
        batch.setColor(1,1,1,1);
        batch.draw(money, startX, startY+5, 70f, 70f);
        this.font.getData().setScale(2f);
        font.draw(batch, String.valueOf(moneyAmount), startX+70, startY+52);
        this.font.getData().setScale(1.5f);
        font.draw(batch, "Press Esc for help", 25, startY+47.5f);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        money.dispose();
        statusBarTexture.dispose();
    }
}
