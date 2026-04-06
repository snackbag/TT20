package net.snackbag.tt20.util;

public enum MaskType {
    WHITELIST,
    BLACKLIST;

    public static MaskType fromString(String input) {
        return switch (input.toLowerCase()) {
            case "whitelist" -> WHITELIST;
            case "blacklist" -> BLACKLIST;
            default -> throw new IllegalArgumentException("'" + input + "' is not a valid mask type");
        };
    }
}
