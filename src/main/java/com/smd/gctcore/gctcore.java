package com.smd.gctcore;

import com.smd.gctcore.events.EventHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class gctcore {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHooks());
        DimensionManager.registerDimension(114514, DimensionTypeAirport.Airport);
        DimensionManager.registerDimension(-114514, DimensionTypeNothingness.nothingness);
        DimensionManager.registerDimension(103, DimensionTypeOrderCore.ordercore);
    }
}
