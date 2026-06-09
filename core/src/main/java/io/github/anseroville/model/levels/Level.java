package io.github.anseroville.model.levels;

import io.github.anseroville.model.quest.Quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {
    private final int number;
    private final LevelStartState startState;
    private final List<Quest> sideQuests;
    private final Quest mainQuest;
    private final float wateringModifier;
    private final float growingModifier;

    public Level(
            int number,
            LevelStartState startState,
            List<Quest> sideQuests,
            Quest mainQuest,
            float wateringModifier,
            float growingModifier
    ) {
        this.number = number;
        this.startState = startState;
        this.sideQuests = new ArrayList<>(sideQuests);
        this.mainQuest = mainQuest;
        this.growingModifier = growingModifier;
        this.wateringModifier = wateringModifier;
    }

    public int getNumber() {
        return number;
    }

    public LevelStartState getStartState() {
        return startState;
    }

    public List<Quest> getSideQuests() {
        return Collections.unmodifiableList(sideQuests);
    }

    public Quest getMainQuest() {
        return mainQuest;
    }

    public int getSideQuestsCount() {
        return sideQuests.size();
    }

    public float getWateringModifier() {
        return wateringModifier;
    }

    public float getGrowingModifier() {
        return growingModifier;
    }
}