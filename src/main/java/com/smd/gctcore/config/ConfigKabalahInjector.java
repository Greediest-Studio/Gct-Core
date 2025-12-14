package com.smd.gctcore.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConfigKabalahInjector {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void inject(File configDir) {
        List<ConfigHandler.KabalahRecipe> recipes = ConfigHandler.getKabalahRecipes();
        if (recipes == null || recipes.isEmpty()) return;

        if (Loader.isModLoaded("gct_mobs")) {
            try {
                Class<?> clazz = Class.forName("com.smd.gctmobs.kabalah.KabalahRegistry");

                for (ConfigHandler.KabalahRecipe kr : recipes) {
                    try {
                        // First, try a JSON-based registration if provided by gct_mobs
                        try {
                            Method m = clazz.getDeclaredMethod("addRecipeFromConfig", String.class, String.class);
                            m.setAccessible(true);
                            m.invoke(null, GSON.toJson(kr), "gctcore:config");
                            continue;
                        } catch (NoSuchMethodException ignored) {}

                        // Otherwise, try to build ItemStacks and call register methods
                        List<net.minecraft.item.ItemStack> inputs = new ArrayList<>();
                        for (ConfigHandler.IngredientEntry ie : kr.inputs) {
                            net.minecraft.item.Item it = net.minecraft.item.Item.getByNameOrId(ie.item.toString());
                            int meta = ie.metadata == -1 ? 0 : ie.metadata;
                            inputs.add(new net.minecraft.item.ItemStack(it, ie.count, meta));
                        }
                        net.minecraft.item.Item outIt = net.minecraft.item.Item.getByNameOrId(kr.output.toString());
                        net.minecraft.item.ItemStack output = new net.minecraft.item.ItemStack(outIt, kr.outputCount, kr.outputMetadata == -1 ? 0 : kr.outputMetadata);

                        // Try multiple method signatures
                        try {
                            Method m = clazz.getDeclaredMethod("registerRecipe", ResourceLocation.class, List.class);
                            m.setAccessible(true);
                            m.invoke(null, kr.output, inputs);
                            continue;
                        } catch (NoSuchMethodException | IllegalArgumentException ignored) {}

                        try {
                            Method m = clazz.getDeclaredMethod("registerRecipe", net.minecraft.item.ItemStack.class, java.util.List.class);
                            m.setAccessible(true);
                            m.invoke(null, output, inputs);
                            continue;
                        } catch (NoSuchMethodException | IllegalArgumentException ignored) {}

                        // Last resort: try to append to a public/static field named "recipes"
                        try {
                            Field f = clazz.getDeclaredField("recipes");
                            f.setAccessible(true);
                            Object list = f.get(null);
                            if (list instanceof List) {
                                try {
                                    ((List) list).add(kr);
                                } catch (Throwable t) {
                                    try {
                                        List<net.minecraft.item.ItemStack> inputs2 = new ArrayList<>(inputs);
                                        ((List) list).add(new Object[] { output, inputs2 });
                                    } catch (Throwable ignored) {
                                        // give up on this recipe
                                    }
                                }
                            }
                        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

                    } catch (Throwable t) {
                        // ignore and continue with other recipes
                    }
                }

                return; // attempted injection
            } catch (ClassNotFoundException ignored) {
                // registry class not found, fall through to JSON export
            } catch (Throwable ignored) {
                // reflection failed, fall through to JSON export
            }
        }

        // Fallback: write JSON file to config directory
        File out = new File(configDir, "gctcore_kabalah_recipes.json");
        List<Object> export = new ArrayList<>();
        for (ConfigHandler.KabalahRecipe kr : recipes) export.add(kr);
        try (FileWriter fw = new FileWriter(out)) {
            GSON.toJson(export, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
