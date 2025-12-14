package net.snackbag.tt20.config.rework.utils;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.*;

public class Mask {
    private final MaskType maskType;
    private final Registry<?> registry;
    private final RegistryIndex index;
    private final Set<Identifier> entries;

    public Mask(Registry<?> registry, MaskType maskType, List<String> rawEntries) {
        this.registry = registry;
        this.maskType = maskType;
        this.index = RegistryIndex.getIndex(this.registry);
        this.entries = new HashSet<>();

        for (String entry : rawEntries) {
            entries.addAll(manageEntry(entry));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public List<Identifier> manageEntry(String entry) {
        String[] split = entry.split(":");

        if (split.length != 2) {
            System.err.println("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
            return new ArrayList<>();
        }

        // if *:*
        if (split[0].equals("*") && split[1].equals("*")) {
            return index.getIdentifiers();
        }

        // if <namespace>:<path>
        if (!split[0].equals("*") && !split[1].equals("*")) {
            Identifier id = Identifier.of(split[0], split[1]);
            if (id == null) {
                System.err.println("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
                return new ArrayList<>();
            }
            List<Identifier> result = new ArrayList<>(1);
            result.add(id);
            return result;
        }

        // if *:<path>
        if (split[0].equals("*") && !split[1].equals("*")) {
            return index.getPathIndex().getOrDefault(split[1], new ArrayList<>());
        }

        // if <namespace>:*
        if (!split[0].equals("*") && split[1].equals("*")) {
            return index.getNamespaceIndex().getOrDefault(split[0], new ArrayList<>());
        }

        return new ArrayList<>();
    }

    public Registry<?> getRegistry() {
        return registry;
    }

    public MaskType getMaskType() {
        return maskType;
    }

    public boolean matches(Identifier identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(Identifier identifier) {
        return maskType == MaskType.WHITELIST ? entries.contains(identifier) : !entries.contains(identifier);
    }

    public Set<Identifier> getEntries() {
        return Collections.unmodifiableSet(entries);
    }
}
