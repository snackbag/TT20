package net.snackbag.tt20.config.rework;

import net.fabricmc.loader.api.FabricLoader;
import net.snackbag.tt20.config.rework.types.CBoolean;
import net.snackbag.tt20.config.rework.types.CEnum;
import net.snackbag.tt20.config.rework.types.CMask;
import net.snackbag.tt20.config.rework.types.CValue;
import net.snackbag.tt20.config.rework.utils.CCategory;
import net.snackbag.tt20.config.rework.utils.CMap;
import net.snackbag.tt20.config.rework.utils.MaskType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CExporter {
    private static final Logger LOGGER = Logger.getLogger("TT20/Config");

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

        if (!Files.exists(path)) {
            LOGGER.info("Config file " + path.getFileName() + " not found - writing defaults.");
            save(defaults, fileName, format);
            return defaults;
        }

        try {
            String content = Files.readString(path);
            return switch (format) {
                case JSON5 -> parseJson5(content, defaults);
                case TOML  -> parseToml(content, defaults);
            };
        } catch (IOException e) {
            LOGGER.warning("Failed to read config " + path.getFileName() + ": " + e.getMessage());
            return defaults;
        }
    }

    public static boolean save(CMap config, String fileName, Format format) {
        Path path = configPath(fileName, format);

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            LOGGER.warning("Failed to create config directory: " + e.getMessage());
            return false;
        }

        String content = switch (format) {
            case JSON5 -> serializeJson5(config);
            case TOML  -> serializeToml(config);
        };

        try {
            Files.writeString(path, content);
            return true;
        } catch (IOException e) {
            LOGGER.warning("Failed to write config " + path.getFileName() + ": " + e.getMessage());
            return false;
        }
    }

    private static String serializeJson5(CMap config) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        List<CCategory> categories = new ArrayList<>(config.map().keySet());
        for (int ci = 0; ci < categories.size(); ci++) {
            CCategory category = categories.get(ci);
            boolean lastCategory = (ci == categories.size() - 1);

            for (String desc : category.description()) {
                sb.append("    // ").append(desc).append("\n");
            }

            sb.append("    \"").append(category.id()).append("\": {\n");

            List<Map.Entry<String, CValue<?>>> entries =
                    new ArrayList<>(config.map().get(category).entrySet());

            for (int vi = 0; vi < entries.size(); vi++) {
                boolean lastValue = (vi == entries.size() - 1);
                appendJson5Value(sb, entries.get(vi).getKey(), entries.get(vi).getValue(), lastValue);
            }

            sb.append("    }");
            if (!lastCategory) sb.append(",");
            sb.append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    private static void appendJson5Value(StringBuilder sb, String key, CValue<?> value, boolean last) {
        String comma = last ? "" : ",";

        if (value instanceof CBoolean cBoolean) {
            sb.append("        \"").append(key).append("\": ")
                    .append(cBoolean.get())
                    .append(comma)
                    .append(" // ").append(value.comment)
                    .append("\n");

        } else if (value instanceof CEnum<?> cEnum) {
            sb.append("        \"").append(key).append("\": ")
                    .append("\"").append(cEnum.get().name()).append("\"")
                    .append(comma)
                    .append(" // ").append(value.comment)
                    .append(" [").append(enumOptions(cEnum)).append("]")
                    .append("\n");

        } else if (value instanceof CMask cMask) {
            sb.append("        \"").append(key).append("\": [ // ").append(value.comment).append("\n");
            List<String> masks = List.of(cMask.getMaskStr());
            for (int i = 0; i < masks.size(); i++) {
                sb.append("            \"").append(masks.get(i)).append("\"");
                if (i < masks.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("        ]").append(comma).append("\n");
        }
    }

    private static String serializeToml(CMap config) {
        StringBuilder sb = new StringBuilder();

        for (CCategory category : config.map().keySet()) {
            for (String desc : category.description()) {
                sb.append("# ").append(desc).append("\n");
            }
            sb.append("[").append(category.id()).append("]\n");

            for (Map.Entry<String, CValue<?>> entry : config.map().get(category).entrySet()) {
                appendTomlValue(sb, entry.getKey(), entry.getValue());
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    private static void appendTomlValue(StringBuilder sb, String key, CValue<?> value) {
        if (value instanceof CBoolean cBoolean) {
            sb.append(key).append(" = ")
                    .append(cBoolean.get())
                    .append(" # ").append(value.comment)
                    .append("\n");

        } else if (value instanceof CEnum<?> cEnum) {
            sb.append(key).append(" = ")
                    .append("\"").append(cEnum.get().name()).append("\"")
                    .append(" # ").append(value.comment)
                    .append(" [").append(enumOptions(cEnum)).append("]")
                    .append("\n");

        } else if (value instanceof CMask cMask) {
            sb.append("# ").append(value.comment).append("\n");
            sb.append(key).append(" = [");
            List<String> masks = List.of(cMask.getMaskStr());
            for (int i = 0; i < masks.size(); i++) {
                sb.append("\"").append(masks.get(i)).append("\"");
                if (i < masks.size() - 1) sb.append(", ");
            }
            sb.append("]\n");
        }
    }

    private static CMap parseJson5(String raw, CMap defaults) {
        List<String> lines = stripJson5Comments(raw);

        CCategory currentCategory = null;
        String currentMaskKey = null;
        List<String> maskAccum = new ArrayList<>();

        for (String line : lines) {
            if (line.matches("\"[^\"]+\"\\s*:\\s*\\{")) {
                currentCategory = findCategory(defaults, extractFirstQuoted(line));
                continue;
            }

            if (line.matches("\"[^\"]+\"\\s*:\\s*\\[")) {
                currentMaskKey = extractFirstQuoted(line);
                maskAccum.clear();
                continue;
            }

            if (currentMaskKey != null) {
                if (line.equals("]")) {
                    if (currentCategory != null) {
                        applyMask(defaults, currentCategory, currentMaskKey, maskAccum);
                    }
                    currentMaskKey = null;
                    maskAccum.clear();
                } else if (line.startsWith("\"") && line.endsWith("\"")) {
                    maskAccum.add(line.substring(1, line.length() - 1));
                }
                continue;
            }

            if (currentCategory != null && line.matches("\"[^\"]+\"\\s*:.*")) {
                int colon = line.indexOf(':');
                String key = extractFirstQuoted(line.substring(0, colon + 1));
                String rawValue = line.substring(colon + 1).trim();
                String value = rawValue.replaceAll("^\"(.*)\"$", "$1");
                applyValue(defaults, currentCategory, key, value);
            }
        }

        return defaults;
    }

    private static List<String> stripJson5Comments(String raw) {
        List<String> result = new ArrayList<>();

        for (String line : raw.split("\n")) {
            boolean inQuotes = false;
            StringBuilder clean = new StringBuilder();
            line = line.trim();

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') inQuotes = !inQuotes;
                if (!inQuotes && c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/') break;
                clean.append(c);
            }

            String trimmed = clean.toString().trim();
            if (trimmed.endsWith(",")) trimmed = trimmed.substring(0, trimmed.length() - 1).trim();
            if (trimmed.isEmpty() || trimmed.equals("{") || trimmed.equals("}")) continue;

            result.add(trimmed);
        }

        return result;
    }

    private static CMap parseToml(String raw, CMap defaults) {
        List<String> lines = stripTomlComments(raw);
        CCategory currentCategory = null;

        for (String line : lines) {
            if (line.matches("\\[[^\\[\\]]+\\]")) {
                String id = line.substring(1, line.length() - 1).trim();
                currentCategory = findCategory(defaults, id);
                continue;
            }

            if (currentCategory == null) continue;

            int eq = line.indexOf('=');
            if (eq < 1) continue;

            String key = line.substring(0, eq).trim();
            String rawValue = line.substring(eq + 1).trim();

            if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
                String inner = rawValue.substring(1, rawValue.length() - 1);
                List<String> items = new ArrayList<>();
                for (String item : inner.split(",")) {
                    String clean = item.trim().replaceAll("^\"(.*)\"$", "$1");
                    if (!clean.isEmpty()) items.add(clean);
                }
                applyMask(defaults, currentCategory, key, items);
                continue;
            }

            String value = rawValue.replaceAll("^\"(.*)\"$", "$1");
            applyValue(defaults, currentCategory, key, value);
        }

        return defaults;
    }

    private static List<String> stripTomlComments(String raw) {
        List<String> result = new ArrayList<>();

        for (String line : raw.split("\n")) {
            boolean inQuotes = false;
            int cutAt = -1;

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') inQuotes = !inQuotes;
                if (!inQuotes && c == '#') { cutAt = i; break; }
            }

            String trimmed = (cutAt >= 0 ? line.substring(0, cutAt) : line).trim();
            if (!trimmed.isEmpty()) result.add(trimmed);
        }

        return result;
    }

    private static Path configPath(String fileName, Format format) {
        return FabricLoader.getInstance().getConfigDir()
                .toAbsolutePath()
                .resolve(fileName + format.extension());
    }

    private static String extractFirstQuoted(String segment) {
        int start = segment.indexOf('"');
        if (start < 0) return "";
        int end = segment.indexOf('"', start + 1);
        if (end < 0) return "";
        return segment.substring(start + 1, end);
    }

    private static CCategory findCategory(CMap config, String id) {
        for (CCategory cat : config.map().keySet()) {
            if (cat.id().equals(id)) return cat;
        }
        return null;
    }

    private static void applyValue(CMap config, CCategory category, String key, String value) {
        Map<String, CValue<?>> values = config.map().get(category);
        if (values == null) return;

        CValue<?> target = values.get(key);
        if (target == null) return;

        if (target instanceof CBoolean cBoolean) {
            if (value.equals("true") || value.equals("false")) {
                cBoolean.set(Boolean.parseBoolean(value));
            }
        } else if (target instanceof CEnum<?> cEnum) {
            cEnum.setEnumStr(value.toUpperCase());
        }
    }

    private static void applyMask(CMap config, CCategory category, String key, List<String> items) {
        Map<String, CValue<?>> values = config.map().get(category);
        if (values == null) return;

        CValue<?> target = values.get(key);
        if (!(target instanceof CMask cMask)) return;

        cMask.setMaskStr(new ArrayList<>(items));

        String maskTypeKey = cMask.getMaskTypeKey();
        CValue<?> maskTypeValue = values.get(maskTypeKey);
        if (maskTypeValue instanceof CEnum<?> cEnum && cEnum.getEnumClass() == MaskType.class) {
            cMask.setMaskType((MaskType) cEnum.get());
        }
    }

    private static String enumOptions(CEnum<?> cEnum) {
        StringBuilder sb = new StringBuilder();
        Enum<?>[] constants = cEnum.getEnumClass().getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            sb.append(constants[i].name());
            if (i < constants.length - 1) sb.append(" / ");
        }
        return sb.toString();
    }
}