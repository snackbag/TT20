package net.snackbag.tt20.config.rework;

import net.snackbag.tt20.config.rework.types.CValue;
import net.snackbag.tt20.config.rework.utils.CCategory;
import net.snackbag.tt20.config.rework.utils.CMap;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CConfig {
    private final CMap categories = new CMap();

    protected <T extends CValue<?>> T register(CCategory category, T value) {
        categories.map()
                .computeIfAbsent(category, k -> new LinkedHashMap<>())
                .put(value.key, value);
        return value;
    }

    public Map<CCategory, LinkedHashMap<String, CValue<?>>> getCategories() {
        return categories.map();
    }

    public LinkedHashMap<String, CValue<?>> getValues(CCategory category) {
        return categories.map().get(category);
    }

    public abstract @NotNull String getFileName();

    protected abstract @NotNull CExporter.Format getFormat();

    public CValue<?> getValue(String key) {
        for (CCategory category : categories.map().keySet()) {
            CValue<?> value = categories.map().get(category).get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public boolean reload() {
        CMap loaded = CExporter.load(categories, getFileName(), getFormat());
        if (!loaded.map().isEmpty()) {
            this.categories.map().putAll(loaded.map());
            return CExporter.save(loaded, getFileName(), getFormat());
        } else {
            return false;
        }
    }
}