package net.snackbag.tt20.config.rework.utils;

import org.jetbrains.annotations.NotNull;

public record CCategory(String id, String[] description) {
    public CCategory(String id, String[] description) {
        this.id = id;
        this.description = description.clone();
    }

    public static CCategory of(String id, String... description) {
        return new CCategory(id, description);
    }

    @Override
    public String[] description() {
        return description.clone();
    }

    @Override
    public @NotNull String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CCategory)) return false;
        return id.equals(((CCategory) obj).id);
    }
}
