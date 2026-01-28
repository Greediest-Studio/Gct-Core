package com.smd.gctcore;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "gctcore")
public class ModPotions {
    public static Potion sukhavati;

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        sukhavati = new PotionSukhavati()
                .setRegistryName(new ResourceLocation("gctcore", "sukhavati"))
                .setPotionName("effect.sukhavati");
        event.getRegistry().register(sukhavati);
    }
}
