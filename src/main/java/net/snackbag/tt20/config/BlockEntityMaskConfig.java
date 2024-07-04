package net.snackbag.tt20.config;

import net.minecraft.registry.Registries;
import net.snackbag.tt20.util.Mask;

public class BlockEntityMaskConfig extends JSONConfiguration {
    public BlockEntityMaskConfig() {
        super("block_entity_mask.json");

        String[] defaultMask = {"*:*"};

        putIfEmpty("type", "whitelist");
        putIfEmpty("blocks", defaultMask);

        save();
    }

    public Mask getMask() {
        return new Mask(Registries.BLOCK, this, "blocks");
    }
}
