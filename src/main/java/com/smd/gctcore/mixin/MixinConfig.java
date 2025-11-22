package com.smd.gctcore.mixin;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.*;
import java.util.function.BooleanSupplier;

@SuppressWarnings("unused")
public class MixinConfig implements ILateMixinLoader {

    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = new LinkedHashMap<>();

    static {
        addModdedMixinCFG("mixins.gctcore.aether.json", "aether_legacy");
        addModdedMixinCFG("mixins.gctcore.astralsorcery.json", "astralsorcery");
        addModdedMixinCFG("mixins.gctcore.ageofminecraft.json", "ageofminecraft");
        addModdedMixinCFG("mixins.gctcore.tconstruct.json", "tconstruct");
        addModdedMixinCFG("mixins.gctcore.enderstorage.json", "enderstorage");
        addModdedMixinCFG("mixins.gctcore.extrabotany.json", "extrabotany");
        addModdedMixinCFG("mixins.gctcore.abyssalcraft.json", "abyssalcraft");
    }

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(MIXIN_CONFIGS.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(final String mixinConfig) {
        BooleanSupplier supplier = MIXIN_CONFIGS.get(mixinConfig);
        if (supplier == null) {
            return false;
        }
        return supplier.getAsBoolean();
    }

    private static boolean modLoaded(final String modID) {
        return Loader.isModLoaded(modID);
    }

    private static void addModdedMixinCFG(final String mixinConfig, final String modID) {
        MIXIN_CONFIGS.put(mixinConfig, () -> modLoaded(modID));
    }

    private static void addMixinCFG(final String mixinConfig, final BooleanSupplier conditions) {
        MIXIN_CONFIGS.put(mixinConfig, conditions);
    }

}