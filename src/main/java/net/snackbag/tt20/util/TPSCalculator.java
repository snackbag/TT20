package net.snackbag.tt20.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class TPSCalculator {
    public Long lastTick;
    public Long currentTick;

    public TPSCalculator() {
        ServerTickEvents.START_SERVER_TICK.register(server -> onTick());
    }

    private void onTick() {
        if (currentTick != null) {
            lastTick = currentTick;
        }

        currentTick = System.currentTimeMillis();
    }

    private long getMSPT() {
        return currentTick - lastTick;
    }

    public double getTPS() {
        if (lastTick == null) return -1;
        if (getMSPT() <= 0) return 0.1;

        double tps = 1000 / (double) getMSPT();
        return tps > 20 ? 20 : tps;
    }
}
