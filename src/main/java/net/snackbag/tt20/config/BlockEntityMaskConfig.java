package net.snackbag.tt20.config;

public class BlockEntityMaskConfig extends JSONConfiguration {
    public BlockEntityMaskConfig() {
        super("block_entity_mask.json");

        String[] defaultMask = {"minecraft:*"};

        putIfEmpty("type", "whitelist");
        putIfEmpty("blocks", defaultMask);

        save();
    }
}