package net.snackbag.tt20.util;

import net.snackbag.tt20.TT20;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TPSUtil {
    private static final DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ROOT));

    public static String colorizeTPS(double tps, boolean format) {
        if (tps > 15) {
            return "§a" + (format ? formatTPS(tps) : tps);
        } else if (tps > 10) {
            return "§e" + (format ? formatTPS(tps) : tps);
        } else {
            return "§c" + (format ? formatTPS(tps) : tps);
        }
    }

    public static String formatTPS(double tps) {
        return df.format(tps);
    }

    public static float tt20(float ticks, boolean limitZero) {
        float newTicks = ticks * (float) TT20.TPS_CALCULATOR.getTPS() / 20f;

        if (limitZero) return Math.max(newTicks, 1f);
        else return newTicks;
    }

    public static int tt20(int ticks, boolean limitZero) {
        int newTicks = ticks * (int) TT20.TPS_CALCULATOR.getTPS() / 20;

        if (limitZero) return Math.max(newTicks, 1);
        else return newTicks;
    }

    public static double tt20(double ticks, boolean limitZero) {
        double newTicks = ticks * TT20.TPS_CALCULATOR.getTPS() / 20;

        if (limitZero) return Math.max(newTicks, 1);
        else return newTicks;
    }
}
