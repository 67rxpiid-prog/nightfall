package com.nightfall;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/** Flags chunks with lots of storage blocks (chests, barrels, shulkers, hoppers). */
public class StashFinder extends Module {
    private static final int THRESHOLD = 15; // storage blocks in one chunk
    private int count;
    private BlockPos last;

    public StashFinder() { super("Stash Finder"); }

    @Override public void begin(ChunkPos cp) { count = 0; last = null; }

    @Override
    public void block(BlockState state, int x, int y, int z) {
        Block b = state.getBlock();
        if (b instanceof ChestBlock || b instanceof ShulkerBoxBlock
                || b == Blocks.BARREL || b == Blocks.HOPPER) {
            count++;
            last = new BlockPos(x, y, z);
        }
    }

    @Override
    public void end(ChunkPos cp) {
        if (count >= THRESHOLD && last != null) Results.add("Stash", last);
    }

    @Override public void onDisable() { Results.clearKind("Stash"); }
}
