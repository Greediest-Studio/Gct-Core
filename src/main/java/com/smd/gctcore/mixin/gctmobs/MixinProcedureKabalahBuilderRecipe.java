package com.smd.gctcore.mixin.gctmobs;

import com.smd.gctcore.config.ConfigHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(targets = "net.mcreator.gctmobs.procedure.ProcedureProKabalahBuilderRecipe", remap = false)
public abstract class MixinProcedureKabalahBuilderRecipe {

    @Inject(method = "executeProcedure", at = @At("HEAD"), cancellable = true)
    private static void gctcore$handleConfigDrivenProcedure(java.util.Map<String, Object> dependencies, CallbackInfo ci) {
        try {
            if (!dependencies.containsKey("x") || !dependencies.containsKey("y") || !dependencies.containsKey("z") || !dependencies.containsKey("world")) return;
            int x = ((Integer) dependencies.get("x")).intValue();
            int y = ((Integer) dependencies.get("y")).intValue();
            int z = ((Integer) dependencies.get("z")).intValue();
            World world = (World) dependencies.get("world");

            TileEntity inv = world.getTileEntity(new BlockPos(x, y, z));
            if (!(inv instanceof TileEntityLockableLoot)) return;

            List<ConfigHandler.KabalahRecipe> recipes = ConfigHandler.getKabalahRecipes();
            boolean matched = false;
            for (ConfigHandler.KabalahRecipe kr : recipes) {
                // Check inputs by slot if slot specified, otherwise check presence/count in expected slots 0-9
                boolean ok = true;
                for (ConfigHandler.IngredientEntry ie : kr.inputs) {
                    int slot = ie.slotIndex == -1 ? -1 : ie.slotIndex - 1; // config is 1-based; mcreator slots are 0-based
                    if (slot >= 0) {
                        ItemStack s = ((TileEntityLockableLoot) inv).getStackInSlot(slot);
                        if (s.isEmpty() || !s.getItem().getRegistryName().toString().equals(ie.item.toString()) || s.getCount() < ie.count) {
                            ok = false; break;
                        }
                    } else {
                        // no slot specified: require some slot among 0-9 matches with sufficient count
                        boolean found = false;
                        for (int si = 0; si <= 9; si++) {
                            ItemStack s = ((TileEntityLockableLoot) inv).getStackInSlot(si);
                            if (!s.isEmpty() && s.getItem().getRegistryName().toString().equals(ie.item.toString()) && s.getCount() >= ie.count) {
                                if (ie.metadata != -1) {
                                    if (s.getMetadata() == ie.metadata) { found = true; break; }
                                } else { found = true; break; }
                            }
                        }
                        if (!found) { ok = false; break; }
                    }
                }
                if (ok) {
                    // matched: set output slot 13 to the output
                    net.minecraft.item.Item it = net.minecraft.item.Item.getByNameOrId(kr.output.toString());
                    ItemStack out = new ItemStack(it, kr.outputCount, kr.outputMetadata == -1 ? 0 : kr.outputMetadata);
                    ((TileEntityLockableLoot) inv).setInventorySlotContents(13, out);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                // clear output slot
                ((TileEntityLockableLoot) inv).removeStackFromSlot(13);
            }

            ci.cancel();
        } catch (Throwable t) {
            // On any error, do not block original behaviour
        }
    }
}
