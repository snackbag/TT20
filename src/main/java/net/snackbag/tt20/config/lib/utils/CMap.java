package net.snackbag.tt20.config.lib.utils;

import net.snackbag.tt20.config.lib.types.CValue;

import java.util.LinkedHashMap;
import java.util.Map;

public record CMap(Map<CCategory, LinkedHashMap<String, CValue<?>>> map) {
    public CMap() {
        this(new LinkedHashMap<>());
    }
}