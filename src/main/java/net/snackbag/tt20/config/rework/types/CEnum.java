package net.snackbag.tt20.config.rework.types;

import java.util.Arrays;

public class CEnum<T extends Enum<T>> extends CValue<T> {
    private final Class<T> enumClass;
    private final T defaultValue;
    private T value;

    public CEnum(String key, String comment, Class<T> enumClass, T defaultValue) {
        super(key, comment);
        this.enumClass = enumClass;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    public void setEnumStr(String enumStr) {
        this.value = Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(enumStr))
                .findFirst()
                .orElse(defaultValue);
    }

    @Override
    public boolean isDefault() {
        return value == defaultValue;
    }

    public Class<T> getEnumClass() {
        return enumClass;
    }
}
