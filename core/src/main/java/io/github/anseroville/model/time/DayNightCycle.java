package io.github.anseroville.model.time;

public class DayNightCycle {
    private final float dayDuration;
    private final float nightDuration;

    private float timer = 0f;
    private boolean night = false;

//    W każdej klatce zwiększamy timer o delta.
//    Gdy minie czas aktualnej fazy, przełączamy dzień na noc albo noc na dzień.

    public DayNightCycle(float dayDuration, float nightDuration) {
        this.dayDuration = dayDuration;
        this.nightDuration = nightDuration;
    }

    public void update(float delta) {
        timer += delta;

        float currentDuration = getCurrentDuration();

        if (timer >= currentDuration) {
            timer = 0;
            night = !night;
        }
    }

    public boolean isNight() {
        return night;
    }

    public float getRemainingTime() {
        return getCurrentDuration() - timer;
    }

    private float getCurrentDuration() {
        return night ? nightDuration : dayDuration;
    }
}