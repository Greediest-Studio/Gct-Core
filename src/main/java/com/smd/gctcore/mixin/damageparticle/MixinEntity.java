package com.smd.gctcore.mixin.damageparticle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.helpful.EntityFriendlyCreature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(
        method = "isOnSameTeam(Lnet/minecraft/entity/Entity;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void gctcore$disableTeamCheckEntity(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof EntityFriendlyCreature) {
            cir.setReturnValue(false);
        }
    }
}
