package com.smd.gctcore;

import com.smd.gctcore.events.EventHooks;
import com.smd.gctcore.proxy.ClientProxy;
import com.smd.gctcore.world.AirportDim.DimensionTypeAirport;
import com.smd.gctcore.world.NothingnessDim.DimensionTypeNothingness;
import com.smd.gctcore.world.OrderCore.DimensionTypeOrderCore;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "gctcore", name = "Gct Core", version = "1.0.6")
public class gctcore {

    public static final String MODID = "gctcore";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHooks());
        
        // 注册维度
        DimensionManager.registerDimension(114514, DimensionTypeAirport.Airport);
        DimensionManager.registerDimension(-114514, DimensionTypeNothingness.nothingness);
        DimensionManager.registerDimension(103, DimensionTypeOrderCore.ordercore);
        
        // 客户端专用初始化
        if (event.getSide() == Side.CLIENT) {
            ClientProxy clientProxy = new ClientProxy();
            clientProxy.preInit();
        }
    }
}
