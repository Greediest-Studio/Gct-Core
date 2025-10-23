package com.smd.gctcore.world.NothingnessDim;

import com.smd.gctcore.world.chunks.ChunkGeneratorNothingness;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderNothingness extends WorldProvider {

    private static final long Freeze_time = 18000L;
    private boolean timeLocked = true;

    @Override
    public long getWorldTime() {
        return timeLocked ? Freeze_time : world.getWorldTime();
    }

    public void lockTimeAtNoon() {
        this.timeLocked = true;
    }

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