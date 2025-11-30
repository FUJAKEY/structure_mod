package com.example.realisticgen.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;

import com.example.realisticgen.RealisticGenMod;
import com.example.realisticgen.world.feature.SkyscraperFeature;

public class RealisticTrees {
    public static final RegistryKey<ConfiguredFeature<?, ?>> REALISTIC_OAK_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak"));
    public static final RegistryKey<PlacedFeature> REALISTIC_OAK_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak_placed"));

    public static final Feature<DefaultFeatureConfig> SKYSCRAPER_FEATURE = new SkyscraperFeature(DefaultFeatureConfig.CODEC);
    public static final RegistryKey<PlacedFeature> SKYSCRAPER_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "skyscraper_placed"));


    public static void register() {
         // Register Feature
         Registry.register(Registries.FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "skyscraper"), SKYSCRAPER_FEATURE);

         // Add features to biomes
         BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, REALISTIC_OAK_PLACED_KEY);

         // Add Skyscrapers (Surface Structures) - Using LOCAL_MODIFICATIONS or TOP_LAYER_MODIFICATION
         BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.SURFACE_STRUCTURES, SKYSCRAPER_PLACED_KEY);
    }
}
