package io.github.anseroville.viewModel;

public class NightViewState {
    private final boolean night;
    private final boolean hasTorch;
    private final float remainingTime;
    private final boolean hasShield;

    public NightViewState(boolean night, boolean hasTorch, float remainingTime, boolean hasShield) {
        this.night = night;
        this.hasTorch = hasTorch;
        this.remainingTime = remainingTime;
        this.hasShield = hasShield;
    }

    public boolean isNight() {
        return night;
    }

    public boolean hasTorch() {
        return hasTorch;
    }
    public boolean hasShield() {return hasShield;}

    public float getRemainingTime() {
        return remainingTime;
    }
}