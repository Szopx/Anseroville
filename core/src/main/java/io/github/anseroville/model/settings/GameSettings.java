package io.github.anseroville.model.settings;

import io.github.anseroville.enums.MovementSpeed;
import io.github.anseroville.enums.PlantGrowthSpeed;

public class GameSettings {
    private MovementSpeed movementSpeed;
    private PlantGrowthSpeed plantGrowthSpeed;
    private boolean nightCycleEnabled;
    private boolean questPanelVisible;
    private boolean showFps;

    public GameSettings() {
        resetToDefaults();
    }

    public void resetToDefaults() {
        movementSpeed = MovementSpeed.NORMAL;
        plantGrowthSpeed = PlantGrowthSpeed.NORMAL;
        nightCycleEnabled = true;
        questPanelVisible = true;
        showFps = false;
    }

    public MovementSpeed getMovementSpeed() {
        return movementSpeed;
    }

    public void cycleMovementSpeed() {
        movementSpeed = movementSpeed.next();
    }

    public PlantGrowthSpeed getPlantGrowthSpeed() {
        return plantGrowthSpeed;
    }

    public void cyclePlantGrowthSpeed() {
        plantGrowthSpeed = plantGrowthSpeed.next();
    }

    public float getPlantGrowthMultiplier() {
        return plantGrowthSpeed.getGrowthMultiplier();
    }

    public boolean isNightCycleEnabled() {
        return nightCycleEnabled;
    }

    public void toggleNightCycle() {
        nightCycleEnabled = !nightCycleEnabled;
    }

    public boolean isQuestPanelVisible() {
        return questPanelVisible;
    }

    public void toggleQuestPanelVisible() {
        questPanelVisible = !questPanelVisible;
    }

    public boolean isShowFps() {
        return showFps;
    }

    public void toggleShowFps() {
        showFps = !showFps;
    }
}