package com.smd.gctcore.world.NothingnessDim;

import com.smd.gctcore.world.chunks.ChunkGeneratorNothingness;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderNothingness extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionTypeNothingness.nothingness;
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }
    @Override
       public IChunkGenerator createChunkGenerator() {
           return new ChunkGeneratorNothingness(world);
       }

    @Override
    public float getCloudHeight(){
        return 255;
    }
}