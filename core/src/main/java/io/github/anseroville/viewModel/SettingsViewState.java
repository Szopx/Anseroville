package io.github.anseroville.viewModel;

public class SettingsViewState {
    private final boolean settingsOpen;
    private final String movementSpeedLabel;
    private final String plantGrowthSpeedLabel;
    private final boolean nightCycleEnabled;
    private final boolean questPanelVisible;
    private final boolean showFps;

    public SettingsViewState(
            boolean settingsOpen,
            String movementSpeedLabel,
            String plantGrowthSpeedLabel,
            boolean nightCycleEnabled,
            boolean questPanelVisible,
            boolean showFps
    ) {
        this.settingsOpen = settingsOpen;
        this.movementSpeedLabel = movementSpeedLabel;
        this.plantGrowthSpeedLabel = plantGrowthSpeedLabel;
        this.nightCycleEnabled = nightCycleEnabled;
        this.questPanelVisible = questPanelVisible;
        this.showFps = showFps;
    }

    public boolean isSettingsOpen() {
        return settingsOpen;
    }

    public String getMovementSpeedLabel() {
        return movementSpeedLabel;
    }

    public String getPlantGrowthSpeedLabel() {
        return plantGrowthSpeedLabel;
    }

    public boolean isNightCycleEnabled() {
        return nightCycleEnabled;
    }

    public boolean isQuestPanelVisible() {
        return questPanelVisible;
    }

    public boolean isShowFps() {
        return showFps;
    }
}