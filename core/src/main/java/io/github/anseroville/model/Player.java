package io.github.anseroville.model;

public class Player {
    private static final int DEFAULT_SPEED = 2;

    private int x;
    private int y;
    private int speed;
    private Direction direction;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = DEFAULT_SPEED;
        this.direction = Direction.UP;
    }

    public void move(Direction direction) {
        this.direction = direction;

        switch (direction) {
            case UP -> y += speed;
            case DOWN -> y -= speed;
            case LEFT -> x -= speed;
            case RIGHT -> x += speed;
        }
    }

    public void teleportToStart() {
        x = 0;
        y = 0;
        direction = Direction.UP;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
}