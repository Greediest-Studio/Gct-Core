package com.smd.gctcore.integration.mmce.adapter;

import static hellfirepvp.modularmachinery.common.registry.RegistryRecipeAdapters.registerAdapter;

import com.smd.gctcore.integration.mmce.adapter.draconicevolution.AdapterDEAwakenedFusionCrafting;
import com.smd.gctcore.integration.mmce.adapter.draconicevolution.AdapterDEBasicFusionCrafting;
import com.smd.gctcore.integration.mmce.adapter.draconicevolution.AdapterDEChaoticFusionCrafting;
import com.smd.gctcore.integration.mmce.adapter.draconicevolution.AdapterDEWyvernFusionCrafting;
import com.smd.gctcore.integration.mmce.adapter.dragonresearch.AdapterDragonResearchFusionCrafting;
import static com.smd.gctcore.integration.mmce.lib.RegistriesMM.DE_BASIC_FUSION_CRAFTING;
import static com.smd.gctcore.integration.mmce.lib.RegistriesMM.DE_CHAOTIC_FUSION_CRAFTING;
import static com.smd.gctcore.integration.mmce.lib.RegistriesMM.DE_AWAKENED_FUSION_CRAFTING;
import static com.smd.gctcore.integration.mmce.lib.RegistriesMM.DE_WYVERN_FUSION_CRAFTING;

import com.smd.gctcore.init.Mods;
import com.smd.gctcore.integration.mmce.adapter.nco.*;
import com.smd.gctcore.integration.mmce.adapter.te5.*;

public class RegRecipeAdapters {

    public static void initialize() {
        if (Mods.THERMAL_EXPANSION.isLoaded()) {
            registerAdapter(new CrucibleRecipeAdapter());
            registerAdapter(new PulverizerRecipeAdapter());
            registerAdapter(new CompactorGearRecipeAdapter());
            registerAdapter(new CompactorPlateRecipeAdapter());
            registerAdapter(new CentrifugeRecipeAdapter());
        }
        if (Mods.NUCLEARCRAFT_OVERHAULED.isLoaded()) {
            registerAdapter(new AdapterNCOIngotFormer());
            registerAdapter(new AdapterNCOCentrifuge());
            registerAdapter(new AdapterNCOElectrolyzer());
        }
        if (Mods.dr.isLoaded()) {
            registerAdapter(new AdapterDragonResearchFusionCrafting());
            /*
            DE_BASIC_FUSION_CRAFTING = registerAdapter(new AdapterDEBasicFusionCrafting());
            DE_WYVERN_FUSION_CRAFTING = registerAdapter(new AdapterDEWyvernFusionCrafting());
            DE_AWAKENED_FUSION_CRAFTING = registerAdapter(new AdapterDEAwakenedFusionCrafting());
            DE_CHAOTIC_FUSION_CRAFTING = registerAdapter(new AdapterDEChaoticFusionCrafting());
             */
        }
    }
}

