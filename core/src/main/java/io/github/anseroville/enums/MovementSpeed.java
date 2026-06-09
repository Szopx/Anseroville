package io.github.anseroville.enums;

public enum MovementSpeed {
    SLOW("SLOW", 2),
    NORMAL("NORMAL", 4),
    FAST("FAST", 8);

    private final String label;
    private final int playerSpeed;

    MovementSpeed(String label, int playerSpeed) {
        this.label = label;
        this.playerSpeed = playerSpeed;
    }

    public String getLabel() {
        return label;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public MovementSpeed next() {
        return switch (this) {
            case SLOW -> NORMAL;
            case NORMAL -> FAST;
            case FAST -> SLOW;
        };
    }
}