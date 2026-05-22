package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.InventoryViewState;

public class InventoryRenderer {
    private static final int ICON_SIZE = 90;
    private final SpriteBatch batch;
    private final Texture carrot;
    private final Texture carrotSeed;
    private final Texture inventory;
    private final BitmapFont font;

    public InventoryRenderer(){
        this.batch=new SpriteBatch();
        this.inventory = new Texture("1779347732838.png");
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
        this.carrot = new Texture("marchewka.png");
        this.carrotSeed = new Texture("nasiona_marchewek.png");
    }

    public void render(InventoryViewState state){
        batch.begin();
        batch.draw(inventory, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        int carrotAmount=state.getAmount(ItemType.CARROT);
        int carrotSeedAmount=state.getAmount(ItemType.CARROT_SEED);
        float startX = Gdx.graphics.getWidth() * 0.12f;
        float startY = Gdx.graphics.getHeight() * 0.73f;
        batch.draw(carrot, startX, startY, ICON_SIZE,ICON_SIZE);
        font.draw(batch, "x " + carrotAmount, startX + 45, startY + 15);
        startY=Gdx.graphics.getHeight()*0.56f;
        batch.draw(carrotSeed, startX, startY, ICON_SIZE,ICON_SIZE);
        font.draw(batch, "x " + carrotSeedAmount, startX + 45, startY + 15);
        batch.end(); //todo petla jak bedzie wiecej grafik
    }

    public void dispose() {
        batch.dispose();
    }
}
