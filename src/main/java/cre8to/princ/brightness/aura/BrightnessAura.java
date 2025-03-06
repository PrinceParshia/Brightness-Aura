package cre8to.princ.brightness.aura;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BrightnessAura implements ClientModInitializer {
	private static boolean isBrightnessMax = true;
	private static boolean wasKeyPressed = false;

	private static final KeyMapping brightnessKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
					"key.brightness-aura.brightness-key",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_B,
					"category.brightness-aura"
			)
	);

	@Override
	public void onInitializeClient() {
	}

	public static boolean isBrightnessMax() {
		if (brightnessKey.isDown()) {
			if (!wasKeyPressed) {
				isBrightnessMax = !isBrightnessMax;
				wasKeyPressed = true;
			}
		} else { wasKeyPressed = false; }

		return isBrightnessMax;
	}
}