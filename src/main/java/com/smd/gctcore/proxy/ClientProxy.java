package com.smd.gctcore.proxy;

import com.smd.gctcore.world.OrderCore.OrderCoreSkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy {
    
    public void preInit() {
        // 注册事件监听器
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        // 完全禁用OrderCore维度的雾效
        if (event.getEntity().world.provider.getDimension() == 103) {
            event.setDensity(0.0f); // 设置为0完全关闭雾效
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onFogColors(EntityViewRenderEvent.FogColors event) {
        // 设置OrderCore维度的雾颜色为透明（相当于关闭雾效）
        if (event.getEntity().world.provider.getDimension() == 103) {
            // 不能取消FogColors事件，但可以设置为透明色来隐藏雾效
            event.setRed(0.0f);
            event.setGreen(0.0f);
            event.setBlue(0.0f);
            // 注意：不调用setCanceled()，因为FogColors事件不可取消
        }
    }
    
    // 这个方法将在WorldProvider中调用
    public static void setSkyRenderer(WorldProvider provider) {
        if (provider.getDimension() == 103) {
            provider.setSkyRenderer(new OrderCoreSkyRenderer());
        }
    }
}