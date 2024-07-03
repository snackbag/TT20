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
        float newTicks = (float) rawTT20(ticks);

        if (limitZero) return newTicks > 0 ? newTicks : 1;
        else return newTicks;
    }

    public static int tt20(int ticks, boolean limitZero) {
        int newTicks = (int) Math.ceil(rawTT20(ticks));

        if (limitZero) return newTicks > 0 ? newTicks : 1;
        else return newTicks;
    }

    public static double tt20(double ticks, boolean limitZero) {
        double newTicks = (double) rawTT20(ticks);

        if (limitZero) return newTicks > 0 ? newTicks : 1;
        else return newTicks;
    }

    public static double rawTT20(double ticks) {
        return ticks * TT20.TPS_CALCULATOR.getAverageTPS() / 20;
    }
}
