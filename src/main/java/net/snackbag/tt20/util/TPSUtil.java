package net.snackbag.tt20.util;

import net.snackbag.tt20.TT20;

public class TPSUtil {
    public static String colorizeTPS(long tps) {
        if (tps > 15) {
            return "§a" + tps;
        } else if (tps > 10) {
            return "§e" + tps;
        } else {
            return "§c" + tps;
        }
    }

    public static float tt20(float ticks) {
        return ticks * TT20.TPS_CALCULATOR.getTPS() / 20f;
    }
}
