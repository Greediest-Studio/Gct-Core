package com.smd.gctcore.common.mixin.mmceaddons;

import mezz.jei.Internal;
import mezz.jei.api.IGuiHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "github.alecsio.mmceaddons.common.integration.jei.render.base.BaseIngredientRenderer", remap = false)
public abstract class MixinBaseIngredientRenderer {

    @Redirect(
            method = "render(Lnet/minecraft/client/Minecraft;IILgithub/alecsio/mmceaddons/common/integration/jei/ingredient/formatting/ITooltippable;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lgithub/alecsio/mmceaddons/common/integration/jei/JeiPlugin;GUI_HELPER:Lmezz/jei/api/IGuiHelper;",
                    opcode = Opcodes.GETSTATIC
            ),
            remap = false,
            require = 1
    )
    private IGuiHelper gctcore$getGuiHelper() {
        // JEI has initialized its helpers before displaying recipes, even if the
        // addon's register callback did not initialize its cached GUI_HELPER.
        return Internal.getHelpers().getGuiHelper();
    }
}
