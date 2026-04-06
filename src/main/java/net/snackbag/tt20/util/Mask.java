package net.snackbag.tt20.util;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.config.JSONConfiguration;

import java.util.*;

public class Mask {
    private final JSONConfiguration file;
    private final MaskType maskType;
    private final Registry<?> registry;
    private final RegistryIndex index;
    //? if >=1.21.11 {
    /*private final Set<Identifier> entries;
    *///?} else {
    private final Set<ResourceLocation> entries;
    //?}

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
    //? if >=1.21.11 {
    /*public List<Identifier> manageEntry(String entry) {
    *///?} else {
    public List<ResourceLocation> manageEntry(String entry) {
    //?}
        String[] split = entry.split(":");

        if (split.length != 2) {
            TT20.LOGGER.error("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
            return new ArrayList<>();
        }

        // if *:*
        if (split[0].equals("*") && split[1].equals("*")) {
            return index.getResourceLocations();
        }

        // if <namespace>:<path>
        if (!split[0].equals("*") && !split[1].equals("*")) {
            return List.of(TT20.id(split[0], split[1]));
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

    //? if >=1.21.11 {
    /*public boolean matches(Identifier identifier) {
    *///?} else {
    public boolean matches(ResourceLocation identifier) {
    //?}
        return entries.contains(identifier);
    }

    //? if >=1.21.11 {
    /*public boolean isOkay(Identifier identifier) {
    *///?} else {
    public boolean isOkay(ResourceLocation identifier) {
    //?}
        if (maskType == MaskType.WHITELIST) {
            return entries.contains(identifier);
        } else {
            return !entries.contains(identifier);
        }
    }
}
