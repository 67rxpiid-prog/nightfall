package com.nightfall;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.ChunkPos;

public abstract class Module {
    public final String name;
    public boolean enabled;

    protected Module(String name) { this.name = name; }

    /** Called before a chunk is scanned. */
    public void begin(ChunkPos cp) {}
    /** Called for every non-air block in the chunk. */
    public void block(BlockState state, int x, int y, int z) {}
    /** Called after a chunk has been scanned. */
    public void end(ChunkPos cp) {}
    /** Called when the module is switched off. */
    public void onDisable() {}
}
