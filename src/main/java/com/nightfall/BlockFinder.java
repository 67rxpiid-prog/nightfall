package com.nightfall;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

/** Finds ores and amethyst in chunks as they load. */
public class BlockFinder extends Module {
    private static final Set<Block> TARGETS = Set.of(
            Blocks.AMETHYST_CLUSTER, Blocks.BUDDING_AMETHYST,
            Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.ANCIENT_DEBRIS, Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.SPAWNER);

    public BlockFinder() { super("Ore / Amethyst Finder"); }

    @Override
    public void block(BlockState state, int x, int y, int z) {
        if (TARGETS.contains(state.getBlock())) {
            Results.add("Ore", new BlockPos(x, y, z));
        }
    }

    @Override
    public void onDisable() { Results.clearKind("Ore"); }
}
