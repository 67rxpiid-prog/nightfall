package com.nightfall;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public class Modules {
    public static final List<Module> ALL = new ArrayList<>();
    public static Theme theme = Theme.BLACK;

    public static void init() {
        ALL.add(new BlockFinder());
        ALL.add(new StashFinder());
        ALL.add(new ActivityFinder());
    }

    public static void toggle(Module m) {
        m.enabled = !m.enabled;
        if (!m.enabled) m.onDisable();
    }

    /** Scans one chunk once and feeds every block to every enabled module. */
    public static void scan(WorldChunk chunk) {
        List<Module> active = new ArrayList<>();
        for (Module m : ALL) if (m.enabled) active.add(m);
        if (active.isEmpty()) return;

        ChunkPos cp = chunk.getPos();
        for (Module m : active) m.begin(cp);

        ChunkSection[] sections = chunk.getSectionArray();
        for (int i = 0; i < sections.length; i++) {
            ChunkSection sec = sections[i];
            if (sec == null || sec.isEmpty()) continue;
            int baseY = (chunk.getBottomSectionCoord() + i) * 16;
            for (int x = 0; x < 16; x++)
                for (int y = 0; y < 16; y++)
                    for (int z = 0; z < 16; z++) {
                        BlockState s = sec.getBlockState(x, y, z);
                        if (s.isAir()) continue;
                        for (Module m : active) m.block(s, cp.getStartX() + x, baseY + y, cp.getStartZ() + z);
                    }
        }
        for (Module m : active) m.end(cp);
    }
}
