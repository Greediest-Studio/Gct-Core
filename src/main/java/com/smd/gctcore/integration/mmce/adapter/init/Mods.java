package com.smd.gctcore.integration.mmce.adapter.init;

import net.minecraftforge.fml.common.Loader;

public enum Mods {
    dr("draconicevolution");

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
