package com.nightfall;

import net.minecraft.block.Block;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

/** Flags chunks with many player-made blocks (a simple base finder). */
public class ActivityFinder extends Module {
    private static final int THRESHOLD = 25; // artificial blocks in one chunk
    private int count;
    private BlockPos last;

    public ActivityFinder() { super("Base / Activity Finder"); }

    @Override public void begin(ChunkPos cp) { count = 0; last = null; }

    @Override
    public void block(BlockState state, int x, int y, int z) {
        Block b = state.getBlock();
        if (b == Blocks.CRAFTING_TABLE || b == Blocks.FURNACE || b == Blocks.BLAST_FURNACE
                || b == Blocks.TORCH || b == Blocks.WALL_TORCH || b == Blocks.LANTERN
                || b == Blocks.BRICKS || b == Blocks.GLASS || b instanceof BedBlock
                || state.isIn(BlockTags.PLANKS) || state.isIn(BlockTags.WOOL)) {
            count++;
            last = new BlockPos(x, y, z);
        }
    }

    @Override
    public void end(ChunkPos cp) {
        if (count >= THRESHOLD && last != null) Results.add("Activity", last);
    }

    @Override public void onDisable() { Results.clearKind("Activity"); }
}
