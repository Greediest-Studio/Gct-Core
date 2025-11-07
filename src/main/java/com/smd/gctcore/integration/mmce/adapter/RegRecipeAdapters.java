package com.smd.gctcore.integration.mmce.adapter;

import static hellfirepvp.modularmachinery.common.registry.RegistryRecipeAdapters.registerAdapter;

import com.smd.gctcore.integration.mmce.adapter.dragonresearch.AdapterDragonResearchFusionCrafting;
import com.smd.gctcore.integration.mmce.adapter.nco.*;
import com.smd.gctcore.integration.mmce.adapter.te5.*;
import hellfirepvp.modularmachinery.common.base.Mods;

public class RegRecipeAdapters {
    public static void initialize() {
        if (Mods.THERMAL_EXPANSION.isPresent()) {
            registerAdapter(new CrucibleRecipeAdapter());
            registerAdapter(new PulverizerRecipeAdapter());
            registerAdapter(new CompactorGearRecipeAdapter());
            registerAdapter(new CompactorPlateRecipeAdapter());
            registerAdapter(new CentrifugeRecipeAdapter());
        }
        if (Mods.NUCLEARCRAFT_OVERHAULED.isPresent()) {
            registerAdapter(new AdapterNCOIngotFormer());
            registerAdapter(new AdapterNCOCentrifuge());
            registerAdapter(new AdapterNCOElectrolyzer());
        }
        if (Mods.DRACONICEVOLUTION.isPresent()) {
            registerAdapter(new AdapterDragonResearchFusionCrafting());
        }
    }
}
