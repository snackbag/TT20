package net.snackbag.tt20.util;

//? if >=1.20.1 {
//?} else {
/*import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
*///?}

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistryIndex {
    private static final HashMap<Registry<?>, RegistryIndex> indexes = new HashMap<>();

    private final Registry<?> registry;
    private final List<ResourceLocation> identifiers;
    private final List<String> namespaces;
    private final List<String> paths;
    private final HashMap<String, List<ResourceLocation>> namespaceIndex;
    private final HashMap<String, List<ResourceLocation>> pathIndex;

    private RegistryIndex(Registry<?> registry) {
        this.registry = registry;

        this.identifiers = new ArrayList<>();
        this.namespaces = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.namespaceIndex = new HashMap<>();
        this.pathIndex = new HashMap<>();

        for (ResourceKey<?> key : registry.registryKeySet()) {
            ResourceLocation identifier = key.location();
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

    public List<ResourceLocation> getResourceLocations() {
        return identifiers;
    }

    public HashMap<String, List<ResourceLocation>> getNamespaceIndex() {
        return new HashMap<>(namespaceIndex);
    }

    public HashMap<String, List<ResourceLocation>> getPathIndex() {
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
