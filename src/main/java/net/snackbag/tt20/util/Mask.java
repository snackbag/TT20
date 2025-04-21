package net.snackbag.tt20.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.snackbag.tt20.TT20;

import java.util.*;
import java.util.stream.Collectors;

public class Mask {
    private final MaskType maskType;
    private final IForgeRegistry<?> registry;
    private final Set<ResourceLocation> entries;

    public Mask(IForgeRegistry<?> registry, String maskType, List<String> maskEntries) {
        this.registry = registry;
        this.maskType = MaskType.fromString(maskType);
        this.entries = new HashSet<>();

        for (String entry : maskEntries) {
            entries.addAll(manageEntry(entry));
        }
    }

    private List<ResourceLocation> manageEntry(String entry) {
        String[] split = entry.split(":");
        if (split.length != 2) {
            TT20.LOGGER.error("(TT20) Invalid mask entry '{}'. Format must be <namespace>:<path>", entry);
            return Collections.emptyList();
        }

        String namespace = split[0];
        String path = split[1];

        // Handle wildcards
        if (namespace.equals("*") && path.equals("*")) {
            return new ArrayList<>(registry.getKeys());
        }

        // Exact match
        if (!namespace.equals("*") && !path.equals("*")) {
            return Collections.singletonList(new ResourceLocation(namespace, path));
        }

        // Wildcard namespace
        if (namespace.equals("*")) {
            return registry.getKeys().stream()
                    .filter(rl -> rl.getPath().equals(path))
                    .collect(Collectors.toList());
        }

        // Wildcard path
        return registry.getKeys().stream()
                .filter(rl -> rl.getNamespace().equals(namespace))
                .collect(Collectors.toList());
    }

    public boolean matches(ResourceLocation identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(ResourceLocation identifier) {
        return (maskType == MaskType.WHITELIST) == matches(identifier);
    }

    public enum MaskType {
        WHITELIST,
        BLACKLIST;

        public static MaskType fromString(String type) {
            return type.equalsIgnoreCase("blacklist") ? BLACKLIST : WHITELIST;
        }
    }
}