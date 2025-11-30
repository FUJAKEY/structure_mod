package com.example.realisticgen.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ComplexSkyscraperFeature extends Feature<DefaultFeatureConfig> {
    public ComplexSkyscraperFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        // Ensure we are on solid ground
        BlockPos groundPos = origin.down();
        // Simple check, can be improved
        if (world.isAir(groundPos)) {
             // Try to find ground down
             for(int i=0; i<10; i++) {
                 if(!world.isAir(groundPos.down(i))) {
                     origin = origin.down(i);
                     break;
                 }
             }
        }

        int width = 13;
        int depth = 13;
        int floors = 10 + random.nextInt(10);
        int floorHeight = 5;

        boolean isOffice = random.nextBoolean();
        BlockState wallBlock = isOffice ? Blocks.CYAN_TERRACOTTA.getDefaultState() : Blocks.WHITE_CONCRETE.getDefaultState();
        BlockState glassBlock = isOffice ? Blocks.BLUE_STAINED_GLASS.getDefaultState() : Blocks.GLASS.getDefaultState();
        BlockState floorBlock = isOffice ? Blocks.POLISHED_ANDESITE.getDefaultState() : Blocks.OAK_PLANKS.getDefaultState();

        for (int f = 0; f < floors; f++) {
            int yBase = origin.getY() + f * floorHeight;

            // Generate Floor Structure
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    for (int y = 0; y < floorHeight; y++) {
                        BlockPos pos = origin.add(x, f * floorHeight + y, z);

                        boolean isWall = x == 0 || x == width - 1 || z == 0 || z == depth - 1;
                        boolean isFloor = y == 0;
                        boolean isCeiling = y == floorHeight - 1;

                        if (isFloor) {
                            world.setBlockState(pos, floorBlock, 3);
                        } else if (isWall) {
                            // Windows pattern
                            if (y > 1 && y < floorHeight - 1 && (x + z) % 2 != 0) {
                                world.setBlockState(pos, glassBlock, 3);
                            } else {
                                world.setBlockState(pos, wallBlock, 3);
                            }
                        } else if (isCeiling) {
                             world.setBlockState(pos, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3);
                             // Lights
                             if (x % 4 == 0 && z % 4 == 0) {
                                 world.setBlockState(pos, Blocks.SEA_LANTERN.getDefaultState(), 3);
                             }
                        } else {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                        }
                    }
                }
            }

            // Elevator Shaft
            for (int y = 0; y < floorHeight; y++) {
                 BlockPos elevatorPos = origin.add(width/2, f * floorHeight + y, depth/2);
                 world.setBlockState(elevatorPos, Blocks.IRON_BLOCK.getDefaultState(), 3);
                 world.setBlockState(elevatorPos.north(), Blocks.LADDER.getDefaultState().with(net.minecraft.block.LadderBlock.FACING, Direction.SOUTH), 3);
            }

            // Furnishings
            if (isOffice) {
                placeOfficeFurniture(world, origin.add(1, f * floorHeight + 1, 1), width-2, depth-2, random);
            } else {
                placeResidentialFurniture(world, origin.add(1, f * floorHeight + 1, 1), width-2, depth-2, random);
            }
        }

        // Roof
        int roofY = origin.getY() + floors * floorHeight;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                world.setBlockState(origin.add(x, roofY, z), Blocks.STONE_BRICKS.getDefaultState(), 3);
            }
        }

        return true;
    }

    private void placeOfficeFurniture(StructureWorldAccess world, BlockPos start, int w, int d, Random random) {
        // Simple desks
        for(int x=2; x<w; x+=4) {
            for(int z=2; z<d; z+=4) {
                world.setBlockState(start.add(x, 0, z), Blocks.POLISHED_BLACKSTONE_STAIRS.getDefaultState(), 3); // Chair
                world.setBlockState(start.add(x+1, 0, z), Blocks.SCAFFOLDING.getDefaultState(), 3); // Table
            }
        }
    }

    private void placeResidentialFurniture(StructureWorldAccess world, BlockPos start, int w, int d, Random random) {
        // Beds and crafting tables
        int beds = random.nextInt(4) + 1;
        for(int i=0; i<beds; i++) {
            int bx = random.nextInt(w-2);
            int bz = random.nextInt(d-2);
            BlockPos pos = start.add(bx, 0, bz);
            if(world.isAir(pos) && world.isAir(pos.south())) {
                 world.setBlockState(pos, Blocks.RED_BED.getDefaultState().with(net.minecraft.block.BedBlock.PART, net.minecraft.block.enums.BedPart.HEAD), 3);
                 world.setBlockState(pos.south(), Blocks.RED_BED.getDefaultState().with(net.minecraft.block.BedBlock.PART, net.minecraft.block.enums.BedPart.FOOT), 3);
            }
        }
    }
}
