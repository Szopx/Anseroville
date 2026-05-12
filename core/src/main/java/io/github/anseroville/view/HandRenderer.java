package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.model.inventory.ItemType;

public class HandRenderer {
    private static final int TILE_SIZE = 75;
    private final SpriteBatch batch;
    private final InventoryViewState viewModel;
    private final Texture carrot;

    public HandRenderer(InventoryViewState viewModel) {
        this.viewModel = viewModel;
        this.batch = new SpriteBatch();
        this.carrot = new Texture("marchewki_3.png");
    }
    public void render(){
        batch.begin();
        batch.draw(carrot, 1, 1, TILE_SIZE, TILE_SIZE); //roboczo (1,1), idk gdzie to chcemy dac
        //to pokazuje co mamy w rece jak cos
        if(viewModel.getHeldItemType()==ItemType.CARROT){
            batch.draw(carrot, 1, 1, TILE_SIZE, TILE_SIZE);
        }
        batch.end();
    }
    public void dispose() {
        batch.dispose();
    }
}
