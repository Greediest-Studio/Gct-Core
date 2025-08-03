package com.smd.gctcore.events;

import com.google.common.collect.BiMap;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.command.CommandDifficulty;
import net.minecraft.command.CommandGameRule;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        BiMap<String, Fluid> masterFluidReference = ObfuscationReflectionHelper.getPrivateValue(FluidRegistry.class, null, "masterFluidReference");
        TextureMap map = event.getMap();

        for (Fluid fluid : masterFluidReference.values()) {
            map.registerSprite(fluid.getStill());
            map.registerSprite(fluid.getFlowing());
        }
    }

    @SubscribeEvent
    public void blockBreakSpeed(PlayerEvent.BreakSpeed event){
        if(!event.getEntityPlayer().onGround && (event.getEntityPlayer().capabilities.isFlying)){
            event.setNewSpeed(event.getOriginalSpeed() * 5);
        }
    }

    @SubscribeEvent
    public static void onCommandEvent(CommandEvent event) {
        ICommand command = event.getCommand();

        if (!(command instanceof CommandDifficulty || command instanceof CommandGameRule)) return;

        ICommandSender sender = event.getSender();
        boolean isDedicated = sender.getServer().isDedicatedServer();
        boolean isPlayer = sender instanceof EntityPlayer;
        boolean isPlayerMP = sender instanceof EntityPlayerMP;
        boolean isConsole = sender instanceof DedicatedServer;

        boolean allow = (isDedicated && isPlayer) || (!isDedicated && (isPlayerMP || isConsole));
        event.setCanceled(!allow);
    }


}
