package net.snackbag.tt20.config.rework.types;

public abstract class CValue<T> {
    public final String key;
    public final String comment;

    protected CValue(String key, String comment) {
        this.key = key;
        this.comment = comment;
    }

    public abstract T get();
    public abstract void set(T value);

    public abstract boolean isDefault();
}