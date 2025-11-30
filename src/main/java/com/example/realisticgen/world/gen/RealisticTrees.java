package com.example.realisticgen.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

import com.example.realisticgen.RealisticGenMod;

public class RealisticTrees {
    public static final RegistryKey<ConfiguredFeature<?, ?>> REALISTIC_OAK_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak"));
    public static final RegistryKey<PlacedFeature> REALISTIC_OAK_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(RealisticGenMod.MOD_ID, "realistic_oak_placed"));

    public static void register() {
         // This method is called during mod init to register biome modifications
         BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.VEGETAL_DECORATION, REALISTIC_OAK_PLACED_KEY);
    }
}
