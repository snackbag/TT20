package net.snackbag.tt20.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class TPSCalculator {
    public Long lastTick;
    public Long currentTick;
    private List<Double> tpsHistory = new ArrayList<>();
    private static final int historyLimit = 100;

    public TPSCalculator() {
        ServerTickEvents.START_SERVER_TICK.register(server -> onTick());
    }

    private void onTick() {
        if (currentTick != null) {
            lastTick = currentTick;
        }

        currentTick = System.currentTimeMillis();
        addToHistory(getTPS());
    }

    private void addToHistory(double tps) {
        if (tpsHistory.size() >= historyLimit) {
            tpsHistory.remove(historyLimit - 1);
        }

        tpsHistory.add(tps);
    }

    private long getMSPT() {
        return currentTick - lastTick;
    }

    public double getAverageTPS() {
        OptionalDouble average = tpsHistory
                .stream()
                .mapToDouble(a -> a)
                .average();
        return average.isPresent() ? average.getAsDouble() : 0.1;
    }

    public double getTPS() {
        if (lastTick == null) return -1;
        if (getMSPT() <= 0) return 0.1;

        double tps = 1000 / (double) getMSPT();
        return tps > 20 ? 20 : tps;
    }
}
