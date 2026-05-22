package io.github.anseroville.viewModel;

public class NightViewState {
    private final boolean night;
    private final boolean hasTorch;
    private final float remainingTime;

    public NightViewState(boolean night, boolean hasTorch, float remainingTime) {
        this.night = night;
        this.hasTorch = hasTorch;
        this.remainingTime = remainingTime;
    }

    public boolean isNight() {
        return night;
    }

    public boolean hasTorch() {
        return hasTorch;
    }

    public float getRemainingTime() {
        return remainingTime;
    }
}