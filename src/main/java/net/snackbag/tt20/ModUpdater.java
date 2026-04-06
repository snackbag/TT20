package net.snackbag.tt20;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snackbag.shit.web.WebRequest;
import net.snackbag.shit.web.WebResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ModUpdater {
    private final static URL updateUrl;
    public static String updateMessage;
    public static boolean hasUpdate = false;

    static {
        try {
            updateUrl = new URL("https://playout.snackbag.net/updater/v1/tt20");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void check() {
        if (!TT20.config.automaticUpdater()) {
            TT20.LOGGER.info("(TT20) Not checking for updates, because the updater is disabled. Current version: " + TT20.VERSION);
            return;
        }

        TT20.LOGGER.info("(TT20) Checking for updates...");

        WebResponse resp;

        try {
            resp = new WebRequest(updateUrl).get();
        } catch (IOException e) {
            throw new RuntimeException("Failed to GET update info for TT20. URL: " + updateUrl);
        }

        JsonObject body = JsonParser.parseString(resp.content()).getAsJsonObject();

        boolean status = body.get("status").getAsBoolean();

        if (!status) {
            throw new RuntimeException("(TT20) Failed to check for updates, status is false.");
        }

        String latest = body.get("latest").getAsString();
        boolean shouldUpdate = checkUpdatesAvailable(latest);

        if (!shouldUpdate) {
            TT20.LOGGER.info("(TT20) Running the latest version");
            return;
        }

        updateMessage = body.get("updateMessage").getAsString();
        hasUpdate = true;
        TT20.LOGGER.warn("(TT20) User is running an outdated version of TT20. Latest: " + latest + " - current: " + TT20.VERSION);
    }

    public static boolean checkUpdatesAvailable(String latest) {
        String[] latestVer = latest.split("\\.");
        String[] oldVer = TT20.VERSION.split("\\.");

        return laterVersion(latestVer, oldVer);
    }

    public static boolean laterVersion(String[] newVer, String[] oldVer) {
        int newVerMajor = Integer.valueOf(newVer[0]);
        int newVerMinor = Integer.valueOf(newVer[1]);
        int newVerPatch = Integer.valueOf(newVer[2]);

        int oldVerMajor = Integer.valueOf(oldVer[0]);
        int oldVerMinor = Integer.valueOf(oldVer[1]);
        int oldVerPatch = Integer.valueOf(oldVer[2]);

        if (newVerMajor > oldVerMajor) {
            return true;
        }

        if (newVerMinor > oldVerMinor) {
            return true;
        }

        return newVerPatch > oldVerPatch;
    }
}
