package com.smd.gctcore.world.OrderCore;

import com.smd.gctcore.world.chunks.ChunkGeneratorOrderCore;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderOrderCore extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionTypeOrderCore.ordercore;
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorOrderCore(world);
    }

    @Override
    public float getCloudHeight(){
        return 255;
    }
}
