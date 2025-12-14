package com.smd.gctcore.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigHandler {

    public static class IngredientEntry {
        public final ResourceLocation item;
        public final int count;
        public final int slotIndex; // 1-based slot index, -1 if unspecified
        public final int metadata; // -1 if unspecified

        public IngredientEntry(ResourceLocation item, int count) {
            this(item, count, -1, -1);
        }

        public IngredientEntry(ResourceLocation item, int count, int slotIndex) {
            this(item, count, slotIndex, -1);
        }

        public IngredientEntry(ResourceLocation item, int count, int slotIndex, int metadata) {
            this.item = item;
            this.count = count;
            this.slotIndex = slotIndex;
            this.metadata = metadata;
        }
    }

    public static class KabalahRecipe {
        public final ResourceLocation output;
        public final int outputMetadata;
        public final int outputCount;
        public final List<IngredientEntry> inputs;

        public KabalahRecipe(ResourceLocation output, int outputMetadata, int outputCount, List<IngredientEntry> inputs) {
            this.output = output;
            this.outputMetadata = outputMetadata;
            this.outputCount = outputCount;
            this.inputs = inputs;
        }
    }

    private static final String CATEGORY = "kabalah_builder";
    private static final String KEY = "kabalah_builder_recipes";

    private static final List<KabalahRecipe> kabalahRecipes = new ArrayList<>();
    private static File configDirectory = null;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        try {
            if (configFile != null) configDirectory = configFile.getParentFile();
            config.load();

                String[] defaults = new String[]{
                    // example uses metadata and slot indices: output modid:item@meta 1 => slot:modid:item@meta count
                    "gctcore:example_altar@0 1 => 1:gctcore:gem@0 2, 2:minecraft:diamond@0 1, 11:gctcore:catalyst@0 1",
                    // debug recipe: produces a simple minecraft:apple from common ingredients, no metadata
                    // Format: output amount => [slot:]modid:item[@meta] amount, ...
                    "gctcore:debug_altar 1 => 1:minecraft:stick 1, 2:minecraft:apple 1, 11:minecraft:coal 1"
                };
                String[] entries = config.getStringList(KEY, CATEGORY, defaults,
                    "Custom Kabalah Builder recipes. Format: output_modid:item[@meta] amount => [slot:]modid:item[@meta] amount, ...\nExample: gctcore:example_altar@0 1 => 1:gctcore:gem@0 2, 2:minecraft:diamond@0 1, 11:gctcore:catalyst@0 1");

            kabalahRecipes.clear();
            for (String s : entries) {
                try {
                    KabalahRecipe r = parseRecipe(s);
                    kabalahRecipes.add(r);
                } catch (Exception e) {
                    // ignore malformed entries
                }
            }

            if (config.hasChanged()) {
                config.save();
            }
        } catch (Exception e) {
            // ignore config load errors
        }
    }

    public static List<KabalahRecipe> getKabalahRecipes() {
        return Collections.unmodifiableList(kabalahRecipes);
    }

    public static File getConfigDirectory() {
        return configDirectory;
    }

    private static KabalahRecipe parseRecipe(String s) throws Exception {
        if (s == null) throw new Exception("empty");
        String[] parts = s.split("=>");
        if (parts.length != 2) throw new Exception("bad format");

        String left = parts[0].trim();
        String right = parts[1].trim();

        String[] leftTokens = left.split("\\s+");
        String outRes = leftTokens[0].trim();
        int outCount = 1;
        if (leftTokens.length > 1) {
            outCount = Integer.parseInt(leftTokens[1].trim());
        }

        // support modid:item@meta for output (capture metadata)
        int outMeta = -1;
        int outAt = outRes.indexOf('@');
        String outResPart = outRes;
        if (outAt != -1) {
            outResPart = outRes.substring(0, outAt);
            try { outMeta = Integer.parseInt(outRes.substring(outAt + 1)); } catch (NumberFormatException e) { outMeta = -1; }
        }
        ResourceLocation outRL = parseResourceWithMeta(outResPart);

        List<IngredientEntry> inputs = new ArrayList<>();
        String[] inputsArr = right.split(",");
        for (String in : inputsArr) {
            String t = in.trim();
            if (t.length() == 0) continue;
            String[] tokens = t.split("\\s+");
            String inResToken = tokens[0].trim();
            int slotIndex = -1;
            String resString = inResToken;
            // detect slot prefix like "1:mod:item" (will contain two colons)
            int firstColon = inResToken.indexOf(':');
            int lastColon = inResToken.lastIndexOf(':');
            if (firstColon != -1 && firstColon != lastColon) {
                String maybeSlot = inResToken.substring(0, firstColon);
                try {
                    slotIndex = Integer.parseInt(maybeSlot);
                    resString = inResToken.substring(firstColon + 1);
                } catch (NumberFormatException nfe) {
                    // not a slot prefix, treat whole token as resource
                    slotIndex = -1;
                    resString = inResToken;
                }
            }

            String inRes = resString;
            int inCount = 1;
            if (tokens.length > 1) inCount = Integer.parseInt(tokens[1].trim());
            int meta = -1;
            int at2 = inRes.indexOf('@');
            String inResPart = inRes;
            if (at2 != -1) {
                inResPart = inRes.substring(0, at2);
                try { meta = Integer.parseInt(inRes.substring(at2 + 1)); } catch (NumberFormatException e) { meta = -1; }
            }
            ResourceLocation inRL = parseResourceWithMeta(inResPart);
            inputs.add(new IngredientEntry(inRL, inCount, slotIndex, meta));
        }

        return new KabalahRecipe(outRL, outMeta, outCount, inputs);
    }

    private static ResourceLocation parseResourceWithMeta(String token) throws Exception {
        // Accept formats: modid:item or modid:item@meta
        if (token == null) throw new Exception("empty resource");
        String t = token.trim();
        String resPart = t;
        int at = t.indexOf('@');
        if (at != -1) {
            resPart = t.substring(0, at);
        }
        return new ResourceLocation(resPart);
    }
}
