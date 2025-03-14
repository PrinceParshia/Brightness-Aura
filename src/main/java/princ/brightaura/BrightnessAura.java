package princ.brightaura;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class BrightnessAura implements ClientModInitializer {
	public static final String KATEGORY = "key.categories.brightaura";

	public static final KeyMapping brightnessAuraKey = new KeyMapping(
			"key.brightness.aura",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			KATEGORY
	);

	@Override
	public void onInitializeClient() {
		registerBrightnessAuraKey();
	}

	private double originalGamma;
	public static final double maxGamma = 15.0;
	private int delay;
	private double targetGamma;
	private double stepSize;
	private boolean execTransition = false;

	public static boolean wasBrightnessAuraKeyPressed = false;

	private void registerBrightnessAuraKey() {
		KeyBindingHelper.registerKeyBinding(brightnessAuraKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (brightnessAuraKey.isDown() && !wasBrightnessAuraKeyPressed) {
				if (!execTransition) {
					if (client.options.gamma().get() != maxGamma) {
						originalGamma = client.options.gamma().get();
						targetGamma = maxGamma;
					} else {
						targetGamma = originalGamma;
					}

					delay = 20;
					stepSize = (targetGamma - client.options.gamma().get()) / delay;
					execTransition = true;
				}
			}

			if (execTransition) {
				if (delay <= 0) {
					client.options.gamma().set(targetGamma);
					execTransition = false;
				} else {
					double newGamma = client.options.gamma().get() + stepSize;
					newGamma = Math.max(0.0, Math.min(maxGamma, newGamma));
					client.options.gamma().set(newGamma);
					delay--;
				}
			}

			wasBrightnessAuraKeyPressed = brightnessAuraKey.isDown();
		});
	}
}