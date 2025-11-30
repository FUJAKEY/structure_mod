package com.example.realisticgen.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SkyscraperFeature extends Feature<DefaultFeatureConfig> {
    public SkyscraperFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // Check for valid ground (Dirt/Grass/Stone)
        BlockPos groundPos = origin.down();
        if (!world.getBlockState(groundPos).isSolidBlock(world, groundPos)) {
            return false;
        }

        int width = 10 + random.nextInt(6); // 10-15 blocks wide
        int depth = 10 + random.nextInt(6);
        int height = 40 + random.nextInt(40); // 40-80 blocks high

        // Simple hollow skyscraper
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    BlockPos pos = origin.add(x, y, z);

                    boolean isEdge = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                    boolean isFloor = y % 5 == 0;

                    if (isEdge) {
                        if (y == 0 || y % 5 == 0) {
                            world.setBlockState(pos, Blocks.IRON_BLOCK.getDefaultState(), 3);
                        } else {
                            world.setBlockState(pos, Blocks.GLASS.getDefaultState(), 3);
                        }
                    } else if (isFloor) {
                         world.setBlockState(pos, Blocks.QUARTZ_BLOCK.getDefaultState(), 3);
                         // Add lighting
                         if (x == width / 2 && z == depth / 2) {
                              world.setBlockState(pos, Blocks.SEA_LANTERN.getDefaultState(), 3);
                         }
                    } else if (x == width / 2 && z == depth / 2) {
                        // Central pillar/elevator
                         world.setBlockState(pos, Blocks.IRON_BLOCK.getDefaultState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
