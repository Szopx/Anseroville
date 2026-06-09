package io.github.anseroville.model.systems;
import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {
    private static final int MAP_MIN_X = 0;
    private static final int MAP_MAX_X = 1920;
    private static final int MAP_MIN_Y = 0;
    private static final int MAP_MAX_Y = 1080;

    private final Rectangle forestBounds = new Rectangle(0, 640, 1920, 440);

    private static final int PLAYER_WIDTH = 20;
    private static final int PLAYER_HEIGHT = 20;

    public boolean canMoveTo(int targetX, int targetY) {
        if (targetX < MAP_MIN_X || targetX + PLAYER_WIDTH > MAP_MAX_X ||
                targetY < MAP_MIN_Y || targetY + PLAYER_HEIGHT > MAP_MAX_Y) {
            return false;
        }

        Rectangle playerRect = new Rectangle(targetX, targetY, PLAYER_WIDTH, PLAYER_HEIGHT);

        return !playerRect.overlaps(forestBounds);
    }
}
