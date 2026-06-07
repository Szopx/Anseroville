package io.github.anseroville.model;

import io.github.anseroville.enums.Direction;

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

    public void setSpeed(int speed) {
        if (speed <= 0) {
            return;
        }

        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}