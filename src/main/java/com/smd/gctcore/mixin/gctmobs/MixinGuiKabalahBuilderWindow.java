package com.smd.gctcore.mixin.gctmobs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.Minecraft;

@Pseudo
@Mixin(targets = "net.mcreator.gctmobs.gui.GuiKabalahBuilder$GuiWindow", remap = false)
public abstract class MixinGuiKabalahBuilderWindow extends GuiContainer {

    // Required constructor bridge for mixin to extend GuiContainer
    public MixinGuiKabalahBuilderWindow() {
        super(null);
    }

    @Inject(method = "drawGuiContainerForegroundLayer", at = @At("HEAD"), cancellable = true)
    private void gctcore$swapKabalahLabels(int mouseX, int mouseY, CallbackInfo ci) {
        // Cancel original label drawing and draw swapped labels instead
        ci.cancel();

        // draw title and swapped labels
        Minecraft mc = Minecraft.getMinecraft();
        int color = -12829636;
        this.fontRenderer.drawString("     Kabalah Builder", 49, 4, color);

        // Original GUI had:
        // Chokmah at (106,28) and Binah at (38,28) -> swap them
        // Chesed at (108,53) and Gevurah at (34,53) -> swap them

        // Draw positions with swapped names
        this.fontRenderer.drawString(" Ain", 3, 78, color);
        this.fontRenderer.drawString(" Soph", 0, 53, color);
        this.fontRenderer.drawString(" Aur", 3, 28, color);
        this.fontRenderer.drawString(" Kether", 72, 16, color);
        this.fontRenderer.drawString(" Binah", 106, 28, color); // was Chokmah
        this.fontRenderer.drawString(" Chokmah", 38, 28, color); // was Binah
        this.fontRenderer.drawString("  Tiphareth", 64, 65, color);
        this.fontRenderer.drawString(" Netzach", 34, 78, color);
        this.fontRenderer.drawString("Hod", 115, 78, color);
        this.fontRenderer.drawString("Yesod", 74, 90, color);
        this.fontRenderer.drawString(" Malkuth", 70, 115, color);
        // swap Chesed/Gevurah
        this.fontRenderer.drawString(" Gevurah", 108, 53, color); // was Chesed
        this.fontRenderer.drawString(" Chesed", 34, 53, color); // was Gevurah
    }
}
