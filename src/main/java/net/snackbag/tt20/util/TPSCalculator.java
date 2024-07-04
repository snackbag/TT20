package net.snackbag.tt20.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.List;

public class TPSCalculator {
    public Long lastTick;
    public Long currentTick;
    private double allMissedTicks = 0;
    private final List<Double> tpsHistory = new ArrayList<>();
    private static final int historyLimit = 40;

    private static final int MAX_TPS = 20;
    private static final int FULL_TICK = 50;

    public TPSCalculator() {
        ServerTickEvents.START_SERVER_TICK.register(server -> onTick());
    }

    private void onTick() {
        if (currentTick != null) {
            lastTick = currentTick;
        }

        currentTick = System.currentTimeMillis();
        addToHistory(getTPS());
        clearMissedTicks();
        missedTick();
    }

    private void addToHistory(double tps) {
        if (tpsHistory.size() >= historyLimit) {
            tpsHistory.remove(0);
        }

        tpsHistory.add(tps);
    }

    public long getMSPT() {
        return currentTick - lastTick;
    }

    public double getAverageTPS() {
        return tpsHistory.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.1);
    }

    public double getTPS() {
        if (lastTick == null) return -1;
        if (getMSPT() <= 0) return 0.1;

        double tps = 1000 / (double) getMSPT();
        return tps > MAX_TPS ? MAX_TPS : tps;
    }

    public void missedTick() {
        if (lastTick == null) return;

        long mspt = getMSPT() <= 0 ? 50 : getMSPT();
        double missedTicks = (mspt / (double) FULL_TICK) - 1;
        allMissedTicks += missedTicks <= 0 ? 0 : missedTicks;
    }

    public double getMostAccurateTPS() {
        return getTPS() > getAverageTPS() ? getAverageTPS() : getTPS();
    }

    public double getAllMissedTicks() {
        return allMissedTicks;
    }

    public int applicableMissedTicks() {
        return (int) Math.floor(allMissedTicks);
    }

    public void clearMissedTicks() {
        allMissedTicks -= applicableMissedTicks();
    }

    public void resetMissedTicks() {
        allMissedTicks = 0;
    }
}
