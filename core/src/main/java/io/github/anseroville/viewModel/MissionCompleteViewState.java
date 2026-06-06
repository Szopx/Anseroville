package io.github.anseroville.viewModel;

public class MissionCompleteViewState {
    private final boolean open;
    private final int completedLevelNumber;
    private final int maxLevelNumber;

    public MissionCompleteViewState(
            boolean open,
            int completedLevelNumber,
            int maxLevelNumber
    ) {
        this.open = open;
        this.completedLevelNumber = completedLevelNumber;
        this.maxLevelNumber = maxLevelNumber;
    }

    public boolean isOpen() {
        return open;
    }

    public int getCompletedLevelNumber() {
        return completedLevelNumber;
    }

    public int getNextLevelNumber() {
        return completedLevelNumber + 1;
    }

    public boolean isFinalLevel() {
        return completedLevelNumber >= maxLevelNumber;
    }

    public String getContinueButtonText() {
        if (isFinalLevel()) {
            return "FINISH GAME";
        }

        return "CONTINUE";
    }
}