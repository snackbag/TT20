package net.snackbag.tt20.config.lib.types;

import net.minecraft.registry.Registries;
import net.snackbag.tt20.config.lib.CConfig;
import net.snackbag.tt20.config.lib.utils.Mask;
import net.snackbag.tt20.config.lib.utils.MaskType;

import java.util.List;

public class CMask extends CValue<Mask> {
    private final Mask defaultMask;
    private final String maskTypeKey;
    private String[] maskStr;
    private Mask mask;
    private MaskType maskType;

    public CMask(String key, String comment, String[] maskStr, String maskTypeKey, CConfig config) {
        super(key, comment);

        this.maskStr = maskStr;
        CValue<?> cValue = config.getValue(maskTypeKey);
        if (cValue instanceof CEnum<?> cEnum && cEnum.getEnumClass() == MaskType.class) {
            maskType = (MaskType) cEnum.get();
        }
        this.defaultMask = new Mask(Registries.BLOCK, maskType, List.of(maskStr));
        this.maskTypeKey = maskTypeKey;
    }

    @Override
    public Mask get() {
        return mask;
    }

    public String[] getMaskStr() {
        return maskStr;
    }

    public void setMaskStr(String[] maskStr) {
        this.maskStr = maskStr;
        this.mask = new Mask(Registries.BLOCK, maskType, List.of(maskStr));
    }

    public void setMaskStr(List<String> maskStr) {
        this.maskStr = maskStr.toArray(new String[0]);
        this.mask = new Mask(Registries.BLOCK, maskType, maskStr);
    }

    public void setMaskType(MaskType maskType) {
        this.maskType = maskType;
        this.mask = new Mask(Registries.BLOCK, maskType, List.of(maskStr));
    }

    public String getMaskTypeKey() {
        return maskTypeKey;
    }

    @Override
    public void set(Mask mask) {
        this.mask = mask;
    }

    @Override
    public boolean isDefault() {
        return mask.getEntries().equals(defaultMask.getEntries());
    }
}
