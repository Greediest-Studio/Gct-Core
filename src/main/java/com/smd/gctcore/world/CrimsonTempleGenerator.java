package com.smd.gctcore.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import java.util.Random;

/**
 * Simple world generator that spawns a small "crimson_temple" structure on surface in the "beside_void" dimension.
 * This implementation is intentionally simple (block placements) to avoid external .nbt dependency.
 */
public class CrimsonTempleGenerator implements IWorldGenerator {

    private static final int CHANCE = 5000; // 1 in CHANCE per chunk

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world == null) return;
        if (!"beside_void".equals(world.provider.getDimensionType().getName())) return;

        if (random.nextInt(CHANCE) != 0) return;

        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);

        int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
        BlockPos pos = new BlockPos(x, y, z);

        // Don't generate on lava surface
        if (world.getBlockState(pos).getBlock() == Blocks.LAVA || world.getBlockState(pos).getBlock() == Blocks.FLOWING_LAVA) {
            return;
        }

        // Try to load template from gct_mobs first, then gctcore
        if (!(world instanceof WorldServer)) return;
        WorldServer ws = (WorldServer) world;
        TemplateManager manager = ws.getStructureTemplateManager();
        Template template = null;
        try {
            template = manager.getTemplate(ws.getMinecraftServer(), new ResourceLocation("gct_mobs", "crimson_temple"));
        } catch (Exception ignored) {}
        if (template == null) {
            try {
                template = manager.getTemplate(ws.getMinecraftServer(), new ResourceLocation("gctcore", "crimson_temple"));
            } catch (Exception ignored) {}
        }

        if (template == null) {
            // No template available; abort generation to avoid placing a fallback structure unexpectedly
            return;
        }

        // Random rotation
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        PlacementSettings settings = new PlacementSettings();
        settings.setRotation(rotation);
        settings.setMirror(Mirror.NONE);

        // Place template at the top surface position without additional offset
        template.addBlocksToWorldChunk(world, pos, settings);
    }
}
