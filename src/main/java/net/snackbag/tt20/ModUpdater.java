package net.snackbag.tt20;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.snackbag.shit.web.WebRequest;
import net.snackbag.shit.web.WebResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("deprecation")
public class ModUpdater {
    private final static URL updateUrl;
    public static String updateMessage;
    public static boolean hasUpdate = false;

    static {
        try {
            updateUrl = new URL("https://playout.snackbag.net/updater/v2/tt20");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void check() {
        if (!TT20.config.automaticUpdater()) {
            TT20.LOGGER.info("(TT20) Not checking for updates, because the updater is disabled. Current patch: " + TT20.PATCH);
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

        body = body.get("unified").getAsJsonObject();

        int latest = body.get("patch").getAsInt();
        boolean shouldUpdate = checkShouldUpdate(latest);

        if (!shouldUpdate) {
            TT20.LOGGER.info("(TT20) Running latest patch (" + TT20.PATCH + ")");
            return;
        }

        updateMessage = body.get("updateMessage").getAsString();
        hasUpdate = true;
        TT20.LOGGER.warn("(TT20) User is running an outdated version of TT20. Latest: " + latest + " - current: " + TT20.PATCH);
    }

    public static boolean checkShouldUpdate(int latestPatch) {
        return latestPatch > TT20.PATCH;
    }
}
