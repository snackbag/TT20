package net.snackbag.tt20.util;

import com.google.gson.JsonElement;
//? if >=1.20.1 {
import net.minecraft.registry.Registry;
//?} else {
/*import net.minecraft.util.registry.Registry;
*///?}
import net.minecraft.util.Identifier;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.config.JSONConfiguration;

import java.util.*;

public class Mask {
    private final JSONConfiguration file;
    private final MaskType maskType;
    private final Registry<?> registry;
    private final RegistryIndex index;
    private final Set<Identifier> entries;

    public Mask(Registry<?> registry, JSONConfiguration file, String maskKey) {
        this.file = file;
        this.maskType = MaskType.fromString(file.getAsString("type"));
        this.registry = registry;
        this.index = RegistryIndex.getIndex(this.registry);
        this.entries = new HashSet<>();

        for (JsonElement element : file.getAsArray(maskKey)) {
            if (!(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())) {
                TT20.LOGGER.error("(TT20) Mask element '" + element + "' isn't a string");
                return;
            }

            entries.addAll(manageEntry(element.getAsString()));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public List<Identifier> manageEntry(String entry) {
        String[] split = entry.split(":");

        if (split.length != 2) {
            TT20.LOGGER.error("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
            return new ArrayList<>();
        }

        // if *:*
        if (split[0].equals("*") && split[1].equals("*")) {
            return index.getIdentifiers();
        }

        // if <namespace>:<path>
        if (!split[0].equals("*") && !split[1].equals("*")) {
            return List.of(Identifier.of(split[0], split[1]));
        }

        // if *:<path>
        if (split[0].equals("*") && !split[1].equals("*")) {
            return index.getPathIndex().getOrDefault(split[1], new ArrayList<>());
        }


        // if <namespace>:*
        if (!split[0].equals("*") && split[1].equals("*")) {
            return index.getNamespaceIndex().getOrDefault(split[0], new ArrayList<>());
        }

        return null;
    }

    public Registry<?> getRegistry() {
        return registry;
    }

    public JSONConfiguration getFile() {
        return file;
    }

    public boolean matches(Identifier identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(Identifier identifier) {
        if (maskType == MaskType.WHITELIST) {
            return entries.contains(identifier);
        } else {
            return !entries.contains(identifier);
        }
    }
}
