package com.smd.gctcore.world.AirportDim;

import com.smd.gctcore.world.chunks.ChunkGeneratorAirport;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderAirport extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionTypeAirport.Airport;
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }
    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorAirport(world);
    }

    @Override
    public float getCloudHeight(){
        return 255;
    }
}