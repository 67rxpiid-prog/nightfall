package com.nightfall;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.List;

/** Small themed overlay listing the closest finds. */
public class Hud {
    public static void render(DrawContext g, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;
        boolean any = false;
        for (Module m : Modules.ALL) if (m.enabled) any = true;
        if (!any) return;

        Theme t = Modules.theme;
        TextRenderer tr = mc.textRenderer;
        List<Results.Entry> list = Results.nearest(mc.player.getX(), mc.player.getY(), mc.player.getZ(), 8);

        int x = 6, y = 6, w = 190, h = 20 + Math.max(1, list.size()) * 11 + 4;
        g.fill(x, y, x + w, y + h, Theme.withAlpha(t.bg, 200));
        g.fill(x, y, x + w, y + 2, t.accent);
        g.drawText(tr, "NIGHTFALL", x + 6, y + 7, t.accent, false);

        int ly = y + 20;
        if (list.isEmpty()) {
            g.drawText(tr, "Nothing found yet", x + 6, ly, t.text, false);
        }
        for (Results.Entry e : list) {
            String line = e.kind() + "  " + e.pos().getX() + " " + e.pos().getY() + " " + e.pos().getZ();
            g.drawText(tr, line, x + 6, ly, t.text, false);
            ly += 11;
        }
    }
}
