package net.snackbag.tt20.util;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.snackbag.shit.config.JSONConfiguration;
import net.snackbag.tt20.TT20;

import java.util.*;
import java.util.stream.Collectors;

public class Mask {
    private final JSONConfiguration file;
    private final MaskType maskType;
    private final IForgeRegistry<?> registry;
    private final Set<ResourceLocation> entries;

    public Mask(IForgeRegistry<?> registry, JSONConfiguration file, String maskKey) {
        this.file = file;
        this.maskType = MaskType.fromString(file.getAsString("type"));
        this.registry = registry;
        this.entries = new HashSet<>();

        for (JsonElement element : file.getAsArray(maskKey)) {
            if (!(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())) {
                TT20.LOGGER.error("(TT20) Mask element '" + element + "' isn't a string");
                return;
            }

            entries.addAll(manageEntry(element.getAsString()));
        }
    }

    private List<ResourceLocation> manageEntry(String entry) {
        String[] split = entry.split(":");

        if (split.length != 2) {
            TT20.LOGGER.error("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
            return Collections.emptyList();
        }

        final String targetNamespace = split[0];
        final String targetPath = split[1];

        return registry.getEntries().stream()
                .map(registryEntry -> registryEntry.getKey().location())
                .filter(location ->
                        (targetNamespace.equals("*") || location.getNamespace().equals(targetNamespace)) &&
                                (targetPath.equals("*") || location.getPath().equals(targetPath))
                )
                .collect(Collectors.toList());
    }

    public IForgeRegistry<?> getRegistry() {
        return registry;
    }

    public JSONConfiguration getFile() {
        return file;
    }

    public boolean matches(ResourceLocation identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(ResourceLocation identifier) {
        return (maskType == MaskType.WHITELIST) == entries.contains(identifier);
    }
}