package com.smd.gctcore.mixin.thaumicrestoration;

import com.Zoko061602.ThaumicRestoration.tile.TileCrystal;
import net.minecraft.util.ITickable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(TileCrystal.class)
public class MixinTileCrystal implements ITickable {

    /**
     * @author Gct-Core
     * @reason 禁用源质结晶效果
     */
    @Overwrite(remap = false)
    public void update() {
    }
}
