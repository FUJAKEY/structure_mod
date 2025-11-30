package com.example.realisticgen;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.structure.pool.StructurePool.Projection;
import com.example.realisticgen.mixin.StructurePoolAccessor;
import java.util.ArrayList;
import java.util.List;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.gen.feature.PlacedFeature;

public class VillageAdditions {
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            Registry<StructurePool> poolRegistry = server.getRegistryManager().get(RegistryKeys.TEMPLATE_POOL);
            Registry<PlacedFeature> placedFeatureRegistry = server.getRegistryManager().get(RegistryKeys.PLACED_FEATURE);

            // Get RegistryEntry for the feature
            RegistryEntry<PlacedFeature> skyscraperFeatureEntry = placedFeatureRegistry.getEntry(
                placedFeatureRegistry.getKey(
                    placedFeatureRegistry.get(Identifier.of(RealisticGenMod.MOD_ID, "skyscraper_placed"))
                ).get()
            ).get();

            addSkyscraperToPool(poolRegistry, Identifier.of("minecraft", "village/plains/houses"), skyscraperFeatureEntry);
            addSkyscraperToPool(poolRegistry, Identifier.of("minecraft", "village/snowy/houses"), skyscraperFeatureEntry);
            addSkyscraperToPool(poolRegistry, Identifier.of("minecraft", "village/savanna/houses"), skyscraperFeatureEntry);
            addSkyscraperToPool(poolRegistry, Identifier.of("minecraft", "village/desert/houses"), skyscraperFeatureEntry);
            addSkyscraperToPool(poolRegistry, Identifier.of("minecraft", "village/taiga/houses"), skyscraperFeatureEntry);
        });
    }

    private static void addSkyscraperToPool(Registry<StructurePool> registry, Identifier poolId, RegistryEntry<PlacedFeature> featureEntry) {
        StructurePool pool = registry.get(poolId);
        if (pool == null) return;

        StructurePoolElement element = StructurePoolElement.ofFeature(featureEntry).apply(Projection.RIGID);

        // Accessor usage
        StructurePoolAccessor accessor = (StructurePoolAccessor) pool;
        List<Pair<StructurePoolElement, Integer>> elementCounts = new ArrayList<>(accessor.getElementCounts());

        // Add with high weight (e.g., 2)
        elementCounts.add(Pair.of(element, 2));
        accessor.getElementCounts().clear();
        accessor.getElementCounts().addAll(elementCounts);

        ObjectArrayList<StructurePoolElement> elements = new ObjectArrayList<>(accessor.getElements());
        elements.add(element);
        elements.add(element); // Add twice for weight 2

        accessor.setElements(elements);
    }
}
