package io.github.anseroville;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;






public class InteractableTile {

    //lewy dolny róg kafelka
    int x;
    int y;
    public final int size = 65;
    Color color = Color.WHITE;
    InteractableTile(int x, int y) {
        this.x = x;
        this.y = y;

    }
    void rendertile(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        shape.rect(x, y, size, size);// Rysuje kwadrat o boku 20
        shape.end();

    }
}
