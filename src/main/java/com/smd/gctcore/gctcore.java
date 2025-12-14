package com.smd.gctcore;

import com.smd.gctcore.events.EventHooks;
import com.smd.gctcore.proxy.ClientProxy;
import com.smd.gctcore.world.AirportDim.DimensionTypeAirport;
import com.smd.gctcore.world.NothingnessDim.DimensionTypeNothingness;
import com.smd.gctcore.world.OrderCore.DimensionTypeOrderCore;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "gctcore", name = "Gct Core", version = "1.0.6")
public class gctcore {

    public static final String MODID = "gctcore";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHooks());
        // Register MoreTcon bedrock handler for whitelist-based mining restrictions
        MinecraftForge.EVENT_BUS.register(new com.smd.gctcore.events.MoreTconBedrockHandler());
        
        // 注册维度
        DimensionManager.registerDimension(114514, DimensionTypeAirport.Airport);
        DimensionManager.registerDimension(-114514, DimensionTypeNothingness.nothingness);
        DimensionManager.registerDimension(103, DimensionTypeOrderCore.ordercore);
        
        // 客户端专用初始化
        if (event.getSide() == Side.CLIENT) {
            ClientProxy clientProxy = new ClientProxy();
            clientProxy.preInit();
        }

        // Load configuration (including kabalah_builder recipes)
        com.smd.gctcore.config.ConfigHandler.init(event.getSuggestedConfigurationFile());

        // If gct_mobs is present, register CrimsonTempleGenerator to spawn crimson_temple in beside_void
        if (Loader.isModLoaded("gct_mobs")) {
            GameRegistry.registerWorldGenerator(new com.smd.gctcore.world.CrimsonTempleGenerator(), 0);
        }
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // post init tasks
        try {
            com.smd.gctcore.config.ConfigKabalahInjector.inject(com.smd.gctcore.config.ConfigHandler.getConfigDirectory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
