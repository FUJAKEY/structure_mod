package com.example.realisticgen;

import com.example.realisticgen.world.gen.RealisticTrees;

public class RealisticWorldGen {
    public static void init() {
        // Biomes logic was moved or removed to avoid duplication
        RealisticTrees.register();
    }
}
