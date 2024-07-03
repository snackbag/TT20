package net.snackbag.util;

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
}
