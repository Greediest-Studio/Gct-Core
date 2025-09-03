package com.smd.gctcore.registry;

import com.smd.gctcore.Tags;
import com.smd.gctcore.init.GctItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class Registrar {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(GctItems.CHAOTIC_FLUX_CAPACITOR);
    event.getRegistry().register(GctItems.ORDERED_FLUX_CAPACITOR);
    event.getRegistry().register(GctItems.FROSTBURN_FLUX_CAPACITOR);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(GctItems.CHAOTIC_FLUX_CAPACITOR, 0,
                new ModelResourceLocation(GctItems.CHAOTIC_FLUX_CAPACITOR.getRegistryName(), "inventory"));
    ModelLoader.setCustomModelResourceLocation(GctItems.ORDERED_FLUX_CAPACITOR, 0,
        new ModelResourceLocation(GctItems.ORDERED_FLUX_CAPACITOR.getRegistryName(), "inventory"));
    ModelLoader.setCustomModelResourceLocation(GctItems.FROSTBURN_FLUX_CAPACITOR, 0,
        new ModelResourceLocation(GctItems.FROSTBURN_FLUX_CAPACITOR.getRegistryName(), "inventory"));
    }
}
