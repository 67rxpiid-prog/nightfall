package com.nightfall;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class NightfallClient implements ClientModInitializer {
    public static KeyBinding openGui;

    @Override
    public void onInitializeClient() {
        Modules.init();

        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("nightfall", "main"));
        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nightfall.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGui.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new NightfallScreen());
            }
        });

        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> Modules.scan(chunk));

        HudRenderCallback.EVENT.register(Hud::render);
    }
}
