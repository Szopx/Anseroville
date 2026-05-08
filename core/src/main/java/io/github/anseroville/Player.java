package io.github.anseroville;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Map;

public class Player {
    public Point position =  new Point();
    public int speed=2;
    enum Direction{Left,Right,Up,Down}
    Direction mydirection = Direction.Up;
    InteractableTile whatilookat;


    void whataminowlookingat(Map<Point, InteractableTile> mapa){
        int x=this.position.x;
        int y=this.position.y;
        final int reach = 50;
        if (mydirection==Direction.Up){ y+=reach;}
        else if (mydirection==Direction.Down){ y-=reach;}
        else if (mydirection==Direction.Right){ x+=reach;}
        else{x-=reach;}

        InteractableTile pom = mapa.get(new Point(x/75,y/73));
        if (pom != whatilookat){
            try {whatilookat.color = Color.WHITE;}catch(Exception e){}
        whatilookat = pom;
        //System.out.println(mapa.containsKey(new Point(x/75,y/73)));
        try{whatilookat.color = Color.RED;}catch(Exception e){}}
    }
    public Player(int x, int y) {
        position.x = x;
        position.y = y;
    }
    void moveup(){position.y+=speed;
    mydirection=Direction.Up;}
    void movedown(){
        speed*=-1;
        moveup();
        speed*=-1;
        mydirection=Direction.Down;
    }
    void moveleft(){
        position.x-=speed;
        mydirection=Direction.Left;
    }
    void moveright(){
        position.x+=speed;
        mydirection=Direction.Right;
    }
    void renderplayer(ShapeRenderer shape) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.BLUE);
        shape.rect(position.x, position.y, 20, 20); // Rysuje kwadrat o boku 20
        shape.end();
    }

    void tp(){
        position.x=0;
        position.y=0;
    }
}
