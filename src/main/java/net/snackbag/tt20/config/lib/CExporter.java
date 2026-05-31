package net.snackbag.tt20.config.lib;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.snackbag.tt20.config.lib.utils.CMap;

import java.nio.file.Path;

public class CExporter {
    private static final Gson GSON = new Gson();

    public enum Format {
        JSON5(".json5"),
        TOML(".toml");

        private final String extension;

        Format(String extension) {
            this.extension = extension;
        }

        public String extension() {
            return extension;
        }
    }

    public static CMap load(CMap defaults, String fileName, Format format) {
        Path path = configPath(fileName, format);

    }

    public static boolean save(CMap config, String fileName, Format format) {
        Path path = configPath(fileName, format);

    }

    private static Path configPath(String fileName, Format format) {
        return FabricLoader.getInstance().getConfigDir()
                .toAbsolutePath()
                .resolve(fileName + format.extension());
    }
}