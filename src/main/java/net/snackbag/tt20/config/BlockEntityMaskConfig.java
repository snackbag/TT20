package net.snackbag.tt20.config;

import net.minecraft.registry.Registries;
import net.snackbag.tt20.util.Mask;

public class BlockEntityMaskConfig extends JSONConfiguration {
    private Mask mask;
    private boolean reloaded = false;

    public BlockEntityMaskConfig() {
        super("block_entity_mask.json");

        String[] defaultMask = {"*:*"};

        putIfEmpty("type", "whitelist");
        putIfEmpty("blocks", defaultMask);

        save();
        reload();
    }

    public Mask getMask() {
        return mask;
    }

    @Override
    public void reload() {
        super.reload();

        if (reloaded) {
            this.mask = new Mask(Registries.BLOCK, this, "blocks");
        } else {
            reloaded = true;
        }
    }
}
