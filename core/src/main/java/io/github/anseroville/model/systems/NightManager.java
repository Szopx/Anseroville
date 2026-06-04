package io.github.anseroville.model.systems;

import io.github.anseroville.enums.ItemType;
import io.github.anseroville.model.GridPosition;
import io.github.anseroville.model.WorldState;
import io.github.anseroville.model.tiles.GroundTile;
import io.github.anseroville.model.tiles.InteractableTile;
import io.github.anseroville.model.inventory.Inventory;
import io.github.anseroville.model.time.DayNightCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NightManager {
    private static final float DAY_DURATION = 30f;
    private static final float NIGHT_DURATION = 10f;
    private static final int NIGHT_DESTROY_CHANCE = 5;
    private final Inventory inventory;
    private final DayNightCycle dayNightCycle;
    private final Random random;
    private final WorldState worldState;

    private boolean nightEffectApplied = false;
    private boolean torchThisNight = false;

    public NightManager(
            Inventory inventory, WorldState worldState
    ) {
        this.inventory = inventory;
        this.worldState = worldState;
        this.dayNightCycle = new DayNightCycle(DAY_DURATION,NIGHT_DURATION);
        this.random = new Random();
    }

    public void update(float delta) {
        dayNightCycle.update(delta);

        if (dayNightCycle.isNight()) {
            if (!nightEffectApplied) {
                handleNightStart();
                nightEffectApplied = true;
            }

            return;
        }

        nightEffectApplied = false;
        torchThisNight = false;
    }

    public boolean isNight() {
        return dayNightCycle.isNight();
    }

    public boolean hasTorch() {
        return torchThisNight;
    }

    public boolean isNightWithoutTorch() {
        return isNight() && !hasTorch();
    }

    public float getNightRemainingTime() {
        if (!isNight()) {
            return 0f;
        }

        return dayNightCycle.getRemainingTime();
    }

    private void handleNightStart() {
        torchThisNight = inventory.remove(ItemType.TORCH, 1);

        if (torchThisNight) {
            System.out.println("Pochodnia zostala zuzyta na te noc.");
        }

        if (inventory.has(ItemType.SHIELD, 1)) {
            System.out.println("Masz tarcze - uprawy sa bezpieczne.");
            inventory.remove(ItemType.SHIELD, 1);
            return;
        }

        removeRandomCrops();
    }

    private void removeRandomCrops() {
        List<GridPosition> positionsToClear = new ArrayList<>();

        for (Map.Entry<GridPosition, InteractableTile> entry : worldState.getTilesView().entrySet()) {
            InteractableTile tile = entry.getValue();

            if (tile instanceof GroundTile && random.nextInt(NIGHT_DESTROY_CHANCE) == 0 && !((GroundTile) tile).isEmpty()) {
                positionsToClear.add(entry.getKey());
            }
        }

        for (GridPosition position : positionsToClear) {
            GroundTile tile = (GroundTile) worldState.getTile(position);
            tile.clearCrop();
        }

        System.out.println("Noc zniszczyla uprawy: " + positionsToClear.size());
    }
}
