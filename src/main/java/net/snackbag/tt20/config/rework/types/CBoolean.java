package net.snackbag.tt20.config.rework.types;

public class CBoolean extends CValue<Boolean> {
    private final boolean defaultValue;
    private Boolean value;

    public CBoolean(String key, String comment, boolean value) {
        super(key, comment);

        this.value = value;
        this.defaultValue = value;
    }

    public void toggle() {
        this.value = !value;
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public void set(Boolean value) {
        this.value = value;
    }

    @Override
    public boolean isDefault() {
        return value == defaultValue;
    }
}
