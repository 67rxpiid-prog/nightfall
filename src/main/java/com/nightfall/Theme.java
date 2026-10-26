package com.nightfall;

public enum Theme {
    //        background  panel       text        accent
    BLACK(0xFF0C0C0E, 0xFF1E1E24, 0xFFEBEBF0, 0xFF00C8FF),
    WHITE(0xFFF5F5F8, 0xFFE1E1E8, 0xFF141418, 0xFF0096DC),
    CYAN (0xFF061C24, 0xFF0A3644, 0xFFDCFAFF, 0xFF00E5FF);

    public final int bg, panel, text, accent;

    Theme(int bg, int panel, int text, int accent) {
        this.bg = bg; this.panel = panel; this.text = text; this.accent = accent;
    }

    /** Same color with a different transparency (alpha 0-255). */
    public static int withAlpha(int argb, int alpha) {
        return (alpha << 24) | (argb & 0x00FFFFFF);
    }

    public Theme next() {
        Theme[] all = values();
        return all[(ordinal() + 1) % all.length];
    }
}
