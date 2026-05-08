package io.github.anseroville;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AnserovilleGame extends ApplicationAdapter {
    //dziwne dane
    int[][] interactableplaces= {{0,0},{0,1},{0,2},{0,3},{0,4},{0,12},{0,13},{0,14},
        {1,0},{1,1},{1,2},{1,3},{1,4},
        {2,0},{2,1},{2,2},{2,3},{2,4},
        {3,0},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6},
        {4,0},{4,1},{4,2},{4,3},{4,4},{4,5},{4,6},
        {6,1},{6,2},{6,4},{6,5},{6,6}};

    private SpriteBatch batch;
    private Texture tlo;
    private Player player;
    private ShapeRenderer shape;
    private Map<Point, InteractableTile> interactableTiles;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        player = new Player(100,100);
        batch = new SpriteBatch();
        tlo = new Texture("tlo.png");

        //render tła. Nasze tło to na razie grid 15x10
        interactableTiles = new HashMap<>();
        //Poprawić sposób wpisywania


        for (int[] i: interactableplaces) {
            interactableTiles.put(new Point(i[1],i[0]), new InteractableTile(i[1]*75,i[0]*73));
        }






    }
    @Override

    public void render() {
        //game handling
        keyboard();

        //rendering graphics
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(tlo, -60, -40);
        batch.end();

        for (Map.Entry<Point, InteractableTile> wpis : interactableTiles.entrySet()) {
            InteractableTile kafelek = wpis.getValue();
            kafelek.rendertile(shape);
        }
        player.renderplayer(shape); //renderplayer na końcu
    }

    void keyboard(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.moveup();
            player.whataminowlookingat(interactableTiles);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.movedown();
            player.whataminowlookingat(interactableTiles);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.moveleft();
            player.whataminowlookingat(interactableTiles);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.moveright();
            player.whataminowlookingat(interactableTiles);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
            player.tp();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        tlo.dispose();
    }
}
