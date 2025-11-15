package com.smd.gctcore.mixin.tinkersevolution;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TraitEvolved.class)
public abstract class MixinTraitEvolved {

    @Inject(
            method = "applyEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NBTTagCompound;setBoolean(Ljava/lang/String;Z)V"
            ), cancellable = true)
    private void applyEffect(CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Gct_Core
     * @reason 修复耐久
     */
    @Overwrite(remap = false)
    public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
        return newAmount;
    }
}
