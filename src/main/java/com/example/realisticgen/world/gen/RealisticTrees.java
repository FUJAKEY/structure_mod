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
import com.example.realisticgen.world.feature.ComplexSkyscraperFeature;

public class RealisticTrees {
    public static final RegistryKey<ConfiguredFeature<?, ?>> REALISTIC_OAK_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak"));
    public static final RegistryKey<PlacedFeature> REALISTIC_OAK_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak_placed"));

    public static final Feature<DefaultFeatureConfig> COMPLEX_SKYSCRAPER_FEATURE = new ComplexSkyscraperFeature(DefaultFeatureConfig.CODEC);

    // Key for the Village version (Empty placement)
    public static final RegistryKey<PlacedFeature> SKYSCRAPER_VILLAGE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "skyscraper_village_placed"));


    public static void register() {
         // Register Feature
         Registry.register(Registries.FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "complex_skyscraper"), COMPLEX_SKYSCRAPER_FEATURE);

         // Add Realistic Oak to biomes
         BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, REALISTIC_OAK_PLACED_KEY);

         // Removed Global Skyscraper Placement to prevent infinite loading/cascading lag.
         // Skyscrapers will now ONLY generate via Village Structures.
    }
}
