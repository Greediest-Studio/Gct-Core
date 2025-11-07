package com.smd.gctcore.init;

import net.minecraftforge.fml.common.Loader;

public enum Mods {
    THERMAL_EXPANSION("thermalexpansion"),
    NUCLEARCRAFT_OVERHAULED("nuclearcraft"),
    DRAGON_RESEARCH("dragonresearch"),
    dr("draconicevolution"),
    AE2FC("ae2fc"),
    AE2("appliedenergistics2"),
    AST("astralsorcery"),
    BOTANIA("botania"),
    MEKENG("mekeng"),
    MMCE("modularmachinery"),
    TC6("thaumcraft");


    public final String modid;
    private final boolean loaded;

    Mods(String modid) {
        this.modid = modid;
        this.loaded = Loader.isModLoaded(this.modid);
    }

    public boolean isLoaded() {
        return loaded;
    }
}
