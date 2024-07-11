package net.snackbag.tt20.util;

//? if >=1.20.1 {
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
//?} else {
/*import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
*///?}
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistryIndex {
    private static final HashMap<Registry<?>, RegistryIndex> indexes = new HashMap<>();

    private final Registry<?> registry;
    private final List<Identifier> identifiers;
    private final List<String> namespaces;
    private final List<String> paths;
    private final HashMap<String, List<Identifier>> namespaceIndex;
    private final HashMap<String, List<Identifier>> pathIndex;

    private RegistryIndex(Registry<?> registry) {
        this.registry = registry;

        this.identifiers = new ArrayList<>();
        this.namespaces = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.namespaceIndex = new HashMap<>();
        this.pathIndex = new HashMap<>();

        for (RegistryKey<?> key : registry.getKeys()) {
            Identifier identifier = key.getValue();
            String namespace = identifier.getNamespace();
            String path = identifier.getPath();

            if (!namespaces.contains(namespace)) {
                namespaces.add(namespace);
                namespaceIndex.put(namespace, new ArrayList<>());
            }

            if (!paths.contains(path)) {
                paths.add(path);
                pathIndex.put(path, new ArrayList<>());
            }

            this.identifiers.add(identifier);
            namespaceIndex.get(namespace).add(identifier);
            pathIndex.get(path).add(identifier);
        }
    }

    public Registry<?> getRegistry() {
        return registry;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public HashMap<String, List<Identifier>> getNamespaceIndex() {
        return new HashMap<>(namespaceIndex);
    }

    public HashMap<String, List<Identifier>> getPathIndex() {
        return new HashMap<>(pathIndex);
    }

    public List<String> getNamespaces() {
        return new ArrayList<>(namespaces);
    }

    public List<String> getPaths() {
        return new ArrayList<>(paths);
    }

    public static RegistryIndex getIndex(Registry<?> registry) {
        if (indexes.containsKey(registry)) {
            return indexes.get(registry);
        }

        indexes.put(registry, new RegistryIndex(registry));
        return indexes.get(registry);
    }
}
