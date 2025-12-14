package net.snackbag.tt20.config;

//? if >=1.20.1 {
import net.minecraft.registry.Registries;
import net.snackbag.tt20.config.rework.utils.Mask;

//?} else {
/*import net.minecraft.util.registry.Registry;
*///?}
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
            //? if >=1.20.1 {
            //this.mask = new Mask(Registries.BLOCK, this, "blocks");
            //?} else {
            /*this.mask = new Mask(Registry.BLOCK, this, "blocks");
            *///?}
        } else {
            reloaded = true;
        }
    }
}
