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
	private static double originalGamma = 0.5;
	private static final double maxGamma = 15.0;
	private static final int delay = 20;
	private static double targetGamma = 0.5;
	private static boolean transition = false;
	private static double stepSize;
	private static int stepsRemaining;
	private static boolean wasKeyPressed = false;

	public static final KeyMapping brightnessAuraKey = new KeyMapping(
			"key.brightness.aura",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			"key.categories.aura"
	);

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(brightnessAuraKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (brightnessAuraKey.isDown() && !wasKeyPressed) {
				if (!transition) {
					if (client.options.gamma().get() != maxGamma) {
						originalGamma = client.options.gamma().get();
						targetGamma = maxGamma;
					} else {
						targetGamma = originalGamma;
					}
					stepsRemaining = delay;
					stepSize = (targetGamma - client.options.gamma().get()) / stepsRemaining;
					transition = true;
				}
			}

			if (transition) {
				if (stepsRemaining <= 0) {
					client.options.gamma().set(targetGamma);
					transition = false;
				} else {
					double currentGamma = client.options.gamma().get();
					client.options.gamma().set(currentGamma + stepSize);
					stepsRemaining--;
				}
			}

			wasKeyPressed = brightnessAuraKey.isDown();
		});
	}
}