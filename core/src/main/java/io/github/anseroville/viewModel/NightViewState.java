package io.github.anseroville.viewModel;

public class NightViewState {
    private final boolean night;
    private final boolean hasTorch;

    public NightViewState(boolean night, boolean hasTorch, float remainingTime) {
        this.night = night;
        this.hasTorch = hasTorch;
    }

    public boolean isNight() {
        return night;
    }

    public boolean hasTorch() {
        return hasTorch;
    }
}