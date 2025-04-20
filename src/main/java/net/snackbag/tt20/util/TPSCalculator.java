package net.snackbag.tt20.util;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TPSCalculator {
    private Long lastTick;
    private Long currentTick;
    private double allMissedTicks = 0;
    private final List<Double> tpsHistory = new CopyOnWriteArrayList<>();
    private static final int HISTORY_LIMIT = 40;

    public static final int MAX_TPS = 20;
    public static final int FULL_TICK_MS = 50;

    public TPSCalculator() {

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (currentTick != null) {
            lastTick = currentTick;
        }
        currentTick = System.currentTimeMillis();

        double currentTPS = getTPS();
        addToHistory(currentTPS);

        clearMissedTicks();
        recordMissedTicks();
    }

    private void addToHistory(double tps) {
        if (tpsHistory.size() >= HISTORY_LIMIT) {
            tpsHistory.remove(0);
        }
        tpsHistory.add(tps);
    }

    public long getMSPT() {
        if (lastTick == null) return FULL_TICK_MS;
        return currentTick - lastTick;
    }

    public double getTPS() {
        if (lastTick == null) return -1;
        long mspt = getMSPT();
        if (mspt <= 0) return 0.1;
        double tps = 1000.0 / mspt;
        return Math.min(tps, MAX_TPS);
    }

    public double getAverageTPS() {
        return tpsHistory.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.1);
    }

    public double getMostAccurateTPS() {
        double instant = getTPS();
        double avg = getAverageTPS();
        return Math.min(instant, avg);
    }

    private void recordMissedTicks() {
        if (lastTick == null) return;
        long mspt = getMSPT() <= 0 ? FULL_TICK_MS : getMSPT();
        double missed = (mspt / (double) FULL_TICK_MS) - 1;
        if (missed > 0) {
            allMissedTicks += missed;
        }
    }

    private void clearMissedTicks() {
        allMissedTicks -= Math.floor(allMissedTicks);
    }

    public double getAllMissedTicks() {
        return allMissedTicks;
    }

    public int applicableMissedTicks() {
        return (int) Math.floor(allMissedTicks);
    }

    public void resetMissedTicks() {
        allMissedTicks = 0;
    }
}
