package net.snackbag.tt20.config;

import net.minecraft.registry.Registries;
import net.snackbag.tt20.util.Mask;

public class BlockEntityMaskConfig extends JSONConfiguration {
    private Mask mask;

    public BlockEntityMaskConfig() {
        super("block_entity_mask.json");

        this.mask = new Mask(Registries.BLOCK, this, "blocks");

        String[] defaultMask = {"*:*"};

        putIfEmpty("type", "whitelist");
        putIfEmpty("blocks", defaultMask);

        save();
    }

    public Mask getMask() {
        return mask;
    }

    @Override
    public void reload() {
        super.reload();
        this.mask = new Mask(Registries.BLOCK, this, "blocks");
    }
}
