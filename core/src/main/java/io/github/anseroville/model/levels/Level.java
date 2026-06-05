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

    public Level(
            int number,
            LevelStartState startState,
            List<Quest> sideQuests,
            Quest mainQuest
    ) {
        this.number = number;
        this.startState = startState;
        this.sideQuests = new ArrayList<>(sideQuests);
        this.mainQuest = mainQuest;
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
}