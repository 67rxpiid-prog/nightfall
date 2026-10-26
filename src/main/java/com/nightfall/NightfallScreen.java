package com.nightfall;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class NightfallScreen extends Screen {
    private static final int W = 260;

    public NightfallScreen() { super(Text.literal("Nightfall")); }

    private int panelHeight() { return 50 + (Modules.ALL.size() + 1) * 24 + 10; }
    private int panelX() { return (this.width - W) / 2; }
    private int panelY() { return (this.height - panelHeight()) / 2; }

    @Override
    protected void init() {
        int x = panelX() + 10;
        int y = panelY() + 50;

        for (Module m : Modules.ALL) {
            this.addDrawableChild(ButtonWidget.builder(label(m), btn -> {
                Modules.toggle(m);
                btn.setMessage(label(m));
            }).dimensions(x, y, W - 20, 20).build());
            y += 24;
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Theme: " + Modules.theme.name()), btn -> {
            Modules.theme = Modules.theme.next();
            btn.setMessage(Text.literal("Theme: " + Modules.theme.name()));
        }).dimensions(x, y, W - 20, 20).build());
    }

    private Text label(Module m) {
        return Text.literal(m.name + ": " + (m.enabled ? "ON" : "OFF"));
    }

    // Replaces the vanilla blurred background with our themed panel.
    @Override
    public void renderBackground(DrawContext g, int mouseX, int mouseY, float delta) {
        Theme t = Modules.theme;
        g.fill(0, 0, this.width, this.height, 0x88000000);
        int x = panelX(), y = panelY(), h = panelHeight();
        g.fill(x, y, x + W, y + h, t.bg);
        g.fill(x, y, x + W, y + 2, t.accent);
        g.fill(x + 6, y + 36, x + W - 6, y + 37, Theme.withAlpha(t.accent, 120));
    }

    @Override
    public void render(DrawContext g, int mouseX, int mouseY, float delta) {
        super.render(g, mouseX, mouseY, delta);
        Theme t = Modules.theme;
        int x = panelX(), y = panelY();

        // Logo: a small "N" built from rectangles, then the wordmark.
        g.fill(x + 12, y + 10, x + 15, y + 28, t.accent);
        g.fill(x + 21, y + 10, x + 24, y + 28, t.accent);
        g.fill(x + 15, y + 12, x + 18, y + 16, t.accent);
        g.fill(x + 18, y + 16, x + 21, y + 20, t.accent);
        g.drawText(this.textRenderer, "NIGHTFALL", x + 34, y + 14, t.text, false);
        g.drawText(this.textRenderer, "1.21.11", x + 34, y + 24, t.accent, false);
    }
}
