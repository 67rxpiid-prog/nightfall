package com.nightfall;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Results {
    public record Entry(String kind, BlockPos pos) {}

    private static final List<Entry> LIST = new ArrayList<>();
    private static final Set<BlockPos> SEEN = ConcurrentHashMap.newKeySet();
    private static final int MAX = 500;

    public static synchronized void add(String kind, BlockPos pos) {
        if (!SEEN.add(pos)) return;
        LIST.add(new Entry(kind, pos));
        if (LIST.size() > MAX) {
            Entry old = LIST.remove(0);
            SEEN.remove(old.pos());
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && !kind.equals("Ore")) {
            mc.player.sendMessage(Text.literal("[Nightfall] " + kind + " at "
                    + pos.getX() + " " + pos.getY() + " " + pos.getZ()), false);
        }
    }

    public static synchronized List<Entry> nearest(double px, double py, double pz, int n) {
        List<Entry> copy = new ArrayList<>(LIST);
        copy.sort(Comparator.comparingDouble(e -> {
            double dx = e.pos().getX() + 0.5 - px;
            double dy = e.pos().getY() + 0.5 - py;
            double dz = e.pos().getZ() + 0.5 - pz;
            return dx * dx + dy * dy + dz * dz;
        }));
        return copy.subList(0, Math.min(n, copy.size()));
    }

    public static synchronized void clearKind(String kind) {
        LIST.removeIf(e -> {
            if (e.kind().equals(kind)) { SEEN.remove(e.pos()); return true; }
            return false;
        });
    }
}
