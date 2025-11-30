package com.example.realisticgen.mixin;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

@Mixin(StructurePool.class)
public interface StructurePoolAccessor {
    @Accessor("elements")
    @Mutable
    ObjectArrayList<StructurePoolElement> getElements();

    @Accessor("elementCounts")
    @Mutable
    List<Pair<StructurePoolElement, Integer>> getElementCounts();

    @Accessor("elements")
    @Mutable
    void setElements(ObjectArrayList<StructurePoolElement> elements);
}
