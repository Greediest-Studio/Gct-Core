package com.smd.gctcore.mixin.tinkersevolution;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.ArmourTraitEvolved;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmourTraitEvolved.class)
public abstract class MixinArmourTraitEvolved {

    @Inject(
            method = "applyEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NBTTagCompound;setBoolean(Ljava/lang/String;Z)V"
            ), cancellable = true)
    private void applyEffect(CallbackInfo ci) {
        ci.cancel();
    }
}
