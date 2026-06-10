package io.github.anseroville.model.systems;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;

public class CollisionManager {
    private static final int MAP_MIN_X = 0;
    private static final int MAP_MAX_X = 1920;
    private static final int MAP_MIN_Y = 0;
    private static final int MAP_MAX_Y = 1080;

    private final Rectangle forestBounds = new Rectangle(0, 640, 1920, 440);

    private static final int PLAYER_WIDTH = 20;
    private static final int PLAYER_HEIGHT = 20;

    private final Polygon lakeBounds;
    float[] lakeVertices = {
            1320f, 0f,
            1320f, 160f,
            1360f, 160f,
            1360f, 120f,
            1464f, 120f,
            1464f, 160f,
            1504f, 160f,
            1504f, 208f,
            1608f, 208f,
            1608f, 256f,
            1664f, 256f,
            1664f, 296f,
            1712f, 296f,
            1712f, 352f,
            1872f, 352f,
            1920f, 352f,
            1920f, 0f
    };
    public CollisionManager(){
        lakeBounds = new Polygon(lakeVertices);
    }

    public boolean canMoveTo(int targetX, int targetY) {
        if (targetX < MAP_MIN_X || targetX + PLAYER_WIDTH > MAP_MAX_X ||
                targetY < MAP_MIN_Y || targetY + PLAYER_HEIGHT > MAP_MAX_Y) {
            return false;
        }

        Rectangle playerRect = new Rectangle(targetX, targetY, PLAYER_WIDTH, PLAYER_HEIGHT);

        if(!playerRect.overlaps(forestBounds) && !isLakeCollision(targetX, targetY)){
            return true;
        }
        return false;
    }
    private boolean isLakeCollision(float targetX, float targetY) {
        boolean isTouchingWater = lakeBounds.contains(targetX, targetY);

        if (isTouchingWater) {
            return true;
        }

        return false;
    }
}
