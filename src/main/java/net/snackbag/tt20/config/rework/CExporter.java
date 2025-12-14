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

public class CExporter {
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

    private static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir().toAbsolutePath();
    }

    public static CMap load(CMap defaults, String fileName, Format format) {
        String finalFileName = fileName + format.extension();
        Path inputPath = getConfigDir().resolve(finalFileName);

        if (!Files.exists(inputPath)) {
            System.out.println("Config file " + finalFileName + " not found, creating new one.");
            save(defaults, fileName, format);
            return new CMap();
        }

        try {
            String content = Files.readString(inputPath);

            if (format == Format.JSON5) {
                return parseJson5(content, defaults);
            } else if (format == Format.TOML) {
                // TODO: return CMap from parsed TOML
            }

            return defaults;
        } catch (IOException e) {
            e.printStackTrace();
            return defaults;
        }
    }

    public static boolean save(CMap config, String fileName, Format format){
        String finalFileName = fileName + format.extension();

        Path outputPath = getConfigDir().resolve(finalFileName);
        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        StringBuilder builder = new StringBuilder();

        addStart(format, builder);
        for (CCategory category : config.map().keySet()) {
            for (String descStr : category.description()) {
                addComment(format, builder, descStr);
            }
            addCategory(format, builder, category);
            for (Map.Entry<String, CValue<?>> entry : config.map().get(category).entrySet()) {
                addValue(entry.getKey(), entry.getValue(), format, builder);
            }
            addCategoryEnd(format, builder);
        }
        addEnd(format, builder);


        try {
            Files.writeString(outputPath, builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void addStart(Format format, StringBuilder builder) {
        if (format == Format.JSON5) {
            builder.append("{\n");
        }
    }

    private static void addComment(Format format, StringBuilder builder, String comment) {
        if (format == Format.JSON5) {
            builder.append("    // ").append(comment).append("\n");
        } else if (format == Format.TOML) {
            builder.append("# ").append(comment).append("\n");
        }
    }

    private static void addCategory(Format format, StringBuilder builder, CCategory category) {
        if (format == Format.JSON5) {
            builder.append("    \"").append(category.id()).append("\": {\n");
        } else if (format == Format.TOML) {
            builder.append("[").append(category.id()).append("]\n");
        }
    }

    private static void addCategoryEnd(Format format, StringBuilder builder) {
        if (format == Format.JSON5) {
            builder.append("    },\n");
        }
    }

    private static void addValue(String key, CValue<?> value, Format format, StringBuilder builder) {
        if (format == Format.JSON5) {
            builder.append("        \"").append(key).append("\": ");

            if (value instanceof CBoolean cBoolean) {
                builder.append(cBoolean.get()).append(",").append(" // ").append(value.comment).append("\n");
            } else if (value instanceof CEnum<?> cEnum) {
                builder.append("\"").append(cEnum.get().name()).append("\",").append(" // ").append(value.comment);
                addEnumComment(builder, cEnum);
            } else if (value instanceof CMask cMask) {
                builder.append("[ // ").append(value.comment);
                for (String maskStr : cMask.getMaskStr()) {
                    builder.append("\n").append("            \"").append(maskStr).append("\",");
                }
                builder.append("\n").append("        ]").append("\n");
            }
        } else if (format == Format.TOML) {
            if (!(value instanceof CMask)) {
                builder.append(key).append(" = ");
            }
            if (value instanceof CBoolean cBoolean) {
                builder.append(cBoolean.get()).append(" # ").append(value.comment).append("\n");
            } else if (value instanceof CEnum<?> cEnum) {
                builder.append("\"").append(cEnum.get().name()).append("\"").append(" # ").append(value.comment);
                addEnumComment(builder, cEnum);
            } else if (value instanceof CMask cMask) {
                builder.append("# ").append(value.comment).append("\n");
                builder.append(key).append(" = ").append("\"\"\"");
                for (String maskStr : cMask.getMaskStr()) {
                    builder.append("\n    ").append(maskStr).append("\n");
                }
                builder.append("\"\"\"");
            }
        }
    }

    private static void addEnumComment(StringBuilder builder, CEnum<?> cEnum) {
        builder.append(" [");
        for (Enum<?> enumValue : cEnum.getEnumClass().getEnumConstants()) {
            builder.append(enumValue.name()).append(" / ");
        }
        builder.deleteCharAt(builder.length() - 3);
        builder.deleteCharAt(builder.length() - 2);
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]").append("\n");
    }

    private static void addEnd(Format format, StringBuilder builder) {
        if (format == Format.JSON5) {
            builder.append("}");
        }
    }

    private static CMap parseJson5(String json, CMap config) {
        String clearJson = clearJson(json);
        String[] lines = clearJson.split("\n");

        CCategory currentCategory = null;
        String currentMask = null;
        List<String> maskList = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("\"") && line.endsWith("{")) { // category line
                int quoteStart = line.indexOf('"');
                int quoteEnd = line.indexOf('"', quoteStart + 1);

                if (quoteStart != -1 && quoteEnd != -1) {
                    String categoryId = line.substring(quoteStart + 1, quoteEnd);

                    for (CCategory category : config.map().keySet()) {
                        if (category.id().equals(categoryId)) {
                            currentCategory = category;
                            break;
                        }
                    }
                }
            }

            if (line.startsWith("\"") && line.endsWith(",")) { // value line
                line = line.substring(0, line.length() - 1);
                int colonIndex = line.indexOf(':');

                String keyPart = line.substring(0, colonIndex).trim();
                String key = keyPart.replaceAll("^[\"']|[\"']$", "");

                String valuePart = line.substring(colonIndex + 1).trim();
                String value = valuePart.replaceAll("^[\"']|[\"']$", "");
                Boolean booleanValue = null;
                
                if (value.equals("true") || value.equals("false")) {
                    booleanValue = Boolean.parseBoolean(value);
                }

                Map<String, CValue<?>> values = config.map().get(currentCategory);
                for (String vKey : values.keySet()) {
                    if (vKey.equals(key)) {
                        CValue<?> cValue = values.get(vKey);
                        if (booleanValue != null && cValue instanceof CBoolean cBoolean) {
                            cBoolean.set(booleanValue);
                        } else if (value.chars().allMatch(Character::isUpperCase) && cValue instanceof CEnum<?> cEnum) {
                            cEnum.setEnumStr(value);
                        }
                    }
                }
            }

            if (line.startsWith("\"") && line.endsWith("[")) { // mask line start
                int quoteStart = line.indexOf('"');
                int quoteEnd = line.indexOf('"', quoteStart + 1);

                if (quoteStart != -1 && quoteEnd != -1) {
                    currentMask = line.substring(quoteStart + 1, quoteEnd);
                }
            }

            if (currentMask != null && line.startsWith("\"") && line.endsWith("\"")) { // in mask array
                maskList.add(line.substring(1, line.length() - 1));
            }

            if (currentMask != null && line.equals("]")) { // mask line end
                Map<String, CValue<?>> values = config.map().get(currentCategory);
                for (CValue<?> cValue : values.values()) {
                    if (cValue.key.equals(currentMask) && cValue instanceof CMask cMask) {
                        cMask.setMaskStr(maskList);
                        String maskTypeKey = cMask.getMaskTypeKey();
                        CValue<?> maskTypeValue = values.get(maskTypeKey);
                        if (maskTypeValue instanceof CEnum<?> cEnum && cEnum.getEnumClass() == MaskType.class) {
                            cMask.setMaskType((MaskType) cEnum.get());
                        }

                        currentMask = null;
                        maskList.clear();
                        break;
                    }
                }
            }
        }
        return config;
    }

    private static CMap parseToml(String toml, CMap config) {
        String clearJson = clearToml(toml);
        String[] lines = clearJson.split("\n");

        CCategory currentCategory = null;
        String currentMask = null;
        List<String> maskList = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("[") && line.endsWith("]")) { // category line
                String categoryId = line.substring(1, line.length() - 1);

                for (CCategory category : config.map().keySet()) {
                    if (category.id().equals(categoryId)) {
                        currentCategory = category;
                        break;
                    }
                }
            }

            if (line.matches("[A-Za-z]+ = [A-Za-z]+")) { // value line
                int equalsIndex = line.indexOf('=');

                String key = line.substring(0, equalsIndex).trim();

                String valuePart = line.substring(equalsIndex + 1).trim();
                String value = valuePart.replaceAll("^[\"']|[\"']$", "");

                Boolean booleanValue = null;

                if (value.equals("true") || value.equals("false")) {
                    booleanValue = Boolean.parseBoolean(value);
                }

                Map<String, CValue<?>> values = config.map().get(currentCategory);
                for (String vKey : values.keySet()) {
                    if (vKey.equals(key)) {
                        CValue<?> cValue = values.get(vKey);
                        if (booleanValue != null && cValue instanceof CBoolean cBoolean) {
                            cBoolean.set(booleanValue);
                        } else if (value.chars().allMatch(Character::isUpperCase) && cValue instanceof CEnum<?> cEnum) {
                            cEnum.setEnumStr(value);
                        }
                    }
                }
            }

            if (!line.startsWith("\"\"\"") && line.endsWith("\"\"\"")) { // mask line start
                currentMask = line.split("=", 2)[0].trim();
            }

            if (currentMask != null) { // in mask array
                maskList.add(line);
            }

            if (currentMask != null && line.equals("\"\"\"")) { // mask line end
                Map<String, CValue<?>> values = config.map().get(currentCategory);
                for (CValue<?> cValue : values.values()) {
                    if (cValue.key.equals(currentMask) && cValue instanceof CMask cMask) {
                        cMask.setMaskStr(maskList);
                        String maskTypeKey = cMask.getMaskTypeKey();
                        CValue<?> maskTypeValue = values.get(maskTypeKey);
                        if (maskTypeValue instanceof CEnum<?> cEnum && cEnum.getEnumClass() == MaskType.class) {
                            cMask.setMaskType((MaskType) cEnum.get());
                        }

                        currentMask = null;
                        maskList.clear();
                        break;
                    }
                }
            }
        }

        return config;
    }

    private static String clearJson(String json) {
        String[] lines = json.split("\n");
        StringBuilder builder = new StringBuilder();

        for (String line : lines) {
            boolean inQuotes = false;
            StringBuilder cleanLine = new StringBuilder();

            line = line.trim();
            if (line.endsWith(",")) line = line.substring(0, line.length() - 1);
            if (line.isEmpty()) continue;
            if (line.equals("{") || line.equals("}")) continue;

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '"' || c == '\'') {
                    inQuotes = !inQuotes;
                }

                if (!inQuotes && c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                    break;
                }

                cleanLine.append(c);
            }

            String trimmed = cleanLine.toString().trim();
            if (!trimmed.isEmpty()) {
                builder.append(trimmed).append('\n');
            }
        }

        return builder.toString();
    }

    private static String clearToml(String toml) {
        String[] lines = toml.split("\n");
        StringBuilder builder = new StringBuilder();

        for (String line : lines) {
            boolean inQuotes = false;
            int cutIndex = -1;

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == '#' && !inQuotes) {
                    cutIndex = i;
                    break;
                }
            }

            String cleaned = (cutIndex != -1)
                    ? line.substring(0, cutIndex).trim()
                    : line.trim();

            if (!cleaned.isEmpty()) {
                builder.append(cleaned).append('\n');
            }
        }

        return builder.toString();
    }
}