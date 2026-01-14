package com.smd.gctcore.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = "gctcore")
@Config.LangKey("gctcore.config.title")
public class GctCoreConfig {

    @Config.Comment("Integration with MoreTcon mod")
    @Config.Name("Moartcon Integration")
    public static MoreTconIntegration moreTconIntegration = new MoreTconIntegration();

    public static class MoreTconIntegration {
        
        @Config.Comment({
            "List of blocks that should act as bedrock-like blocks (requiring BottomsEnd trait to mine).",
            "Format: modid:blockid@metadata[:soft|:hard]",
            "Examples:",
            "  minecraft:stone@0",
            "  minecraft:obsidian@0",
            "  tconstruct:seared@0",
            "Metadata is optional. If omitted, all metadata values will match.",
            "Use * as wildcard for metadata to match all variants: minecraft:wool@*",
            "Append :soft or :hard to force per-entry soft/hard behavior: minecraft:obsidian@0:hard"
        })
        @Config.Name("Bedrock-like Blocks")
        public String[] bedrockLikeBlocks = new String[] {
            "minecraft:obsidian@0"
        };

        @Config.Comment({
            "If true, blocks in the bedrock-like list will be treated as 'soft bedrock'.",
            "Soft bedrock mines faster than regular bedrock with BottomsEnd tools."
        })
        @Config.Name("Treat as Soft Bedrock")
        public boolean treatAsSoftBedrock = true;
    }

    @Config.Comment("AbyssalCraft")
    @Config.Name("AbyssalCraft")
    public static AbyssalCraft abyssalCraftIntegration = new AbyssalCraft();

    public static class AbyssalCraft {

        @Config.Comment({
                "Enable Oblivion Catalyst Effects"
        })
        @Config.Name("Enable Oblivion Catalyst Effects")
        public boolean enableOblivionCatalystEffects = true;
    }

    @Mod.EventBusSubscriber(modid = "gctcore")
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals("gctcore")) {
                ConfigManager.sync("gctcore", Config.Type.INSTANCE);
                // Refresh bedrock block checker cache when config changes
                com.smd.gctcore.integration.moretcon.BedrockBlockChecker.markDirty();
            }
        }
    }
}
