package net.snackbag.tt20.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.snackbag.tt20.TT20;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public class JSONConfiguration {
    private final String fileName;

    private JsonObject json;
    private final Gson gson;

    public JSONConfiguration(String fileName) {
        this.fileName = fileName;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // load file into configuration
        reload();
    }

    public void save() {
        try {
            File file = new File(getAbsolutePath());
            file.getParentFile().mkdirs();
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            TT20.LOGGER.error("(CottonMC) Failed to save JSONConfiguration '" + fileName + "'");
            e.printStackTrace();
            return;
        }
    }

    public void reload() {
        try {
            File file = new File(getAbsolutePath());
            file.getParentFile().mkdirs();

            // if the file didn't exist before
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write("{}");
                writer.close();
            }

            BufferedReader reader = new BufferedReader(new FileReader(getAbsolutePath()));
            json = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            TT20.LOGGER.error("(CottonMC) Failed to reload JSONConfiguration '" + fileName + "'");
            e.printStackTrace();
        }
    }

    public boolean has(String key) {
        // Create a deep copy of the json object
        JsonElement element = gson.fromJson(json.toString(), JsonElement.class);

        for (String part : splitKey(key)) {
            if (element == null || !element.getAsJsonObject().has(part)) {
                return false;
            }
            element = element.getAsJsonObject().get(part);
        }
        return element != null;
    }

    public void put(String key, @Nullable String value) {
        String[] parts = splitKey(key);
        JsonObject current = json;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];

            if (!current.has(part) || !current.get(part).isJsonObject()) {
                current.add(part, new JsonObject());
            }

            current = current.getAsJsonObject(part);
        }

        if (value == null) {
            current.remove(parts[parts.length - 1]);
            return;
        }

        current.addProperty(parts[parts.length - 1], value);
    }

    public void putIfEmpty(String key, @NotNull String value) {
        Objects.requireNonNull(value);

        if (!has(key)) {
            put(key, value);
        }
    }

    public String getString(String key) {
        JsonElement element = json;

        for (String part : splitKey(key)) {
            if (element == null || !element.getAsJsonObject().has(part)) {
                return null;
            }
            element = element.getAsJsonObject().get(part);
        }

        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()
                ? element.getAsString()
                : null;
    }

    public String getStringOrDefault(String key, String def) {
        if (!has(key)) {
            return def;
        }
        return getString(key);
    }

    public String getFileName() {
        return fileName;
    }

    public String[] keys(String key) {
        // If the key is empty, return all string keys in the root JSON object
        if (key.isEmpty()) {
            Set<String> keys = new HashSet<>();
            collectStringKeys(json, "", keys);
            return keys.toArray(new String[0]);
        }

        // Traverse the JSON structure to the specified key
        JsonElement element = json;
        for (String part : splitKey(key)) {
            if (element == null || !element.getAsJsonObject().has(part)) {
                return new String[0];
            }
            element = element.getAsJsonObject().get(part);
        }

        // If the element is a JSON object, collect its string keys
        if (element != null && element.isJsonObject()) {
            Set<String> keys = new HashSet<>();
            collectStringKeys(element.getAsJsonObject(), "", keys);
            return keys.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    public String[] keys() {
        return keys("");
    }

    public String[] splitKey(String key) {
        return key.split("\\.");
    }

    public String getAbsolutePath() {
        return FabricLoader.getInstance().getGameDir().toAbsolutePath().toString() + "/tt20";
    }

    private void collectStringKeys(JsonObject jsonObject, String prefix, Set<String> keys) {
        for (String key : jsonObject.keySet()) {
            JsonElement element = jsonObject.get(key);
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;

            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                keys.add(fullKey);
            } else if (element.isJsonObject()) {
                collectStringKeys(element.getAsJsonObject(), fullKey, keys);
            }
        }
    }
}