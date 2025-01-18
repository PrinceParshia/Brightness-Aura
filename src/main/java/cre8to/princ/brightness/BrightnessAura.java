package cre8to.princ.brightness;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class BrightnessAura implements ClientModInitializer {
	private static KeyBinding brightnessKey;
	private static boolean isBrightnessMax = false;

	@Override
	public void onInitializeClient() {
		brightnessKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.brightness-aura.brightness-key",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_B,
				"category.brightness-aura"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (brightnessKey.wasPressed()) {
				if (isBrightnessMax) {
					client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
				} else {
					client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
				}
				isBrightnessMax = !isBrightnessMax;
			}
		});
	}
}