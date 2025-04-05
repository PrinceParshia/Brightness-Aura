package princ.brightaura;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance.SliderableValueSet;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

import static princ.brightaura.AuraManager.*;

@Environment(EnvType.CLIENT)
public class BrightnessAura implements ClientModInitializer {
	public static final String CATEGORY = "key.categories.brightaura";

	public static final KeyMapping brightnessAuraKey = new KeyMapping(
			"key.brightness.aura",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			CATEGORY
	);

	public static final KeyMapping nightVisionKey = new KeyMapping(
			"key.fake.nightvision",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_N,
			CATEGORY
	);

	@Override
	public void onInitializeClient() {
		registerBrightnessAuraKey();
		registerNightVisionKey();
	}

	private void registerBrightnessAuraKey() {
		KeyBindingHelper.registerKeyBinding(brightnessAuraKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (brightnessAuraKey.consumeClick()) {
				toggleGamma(true);
			}
			handleGammaTransition();
		});
	}

	private void registerNightVisionKey() {
		KeyBindingHelper.registerKeyBinding(nightVisionKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (nightVisionKey.consumeClick()) {
				toggleFakeNightVision();
			}
		});
	}

	@Environment(EnvType.CLIENT)
	public enum UnitDouble implements SliderableValueSet<Double> {
		INSTANCE;

		@Override
		public Optional<Double> validateValue(Double double_) {
			return double_ >= MIN_GAMMA && double_ <= MAX_GAMMA ? Optional.of(double_) : Optional.empty();
		}

		@Override
		public double toSliderValue(Double double_) {
			return double_ / MAX_GAMMA;
		}

		@Override
		public Double fromSliderValue(double d) {
			return (Math.floor((d * MAX_GAMMA) / GAMMA_SLIDER_INTERVAL)) * GAMMA_SLIDER_INTERVAL;
		}

		@Override
		public Codec<Double> codec() {
			return Codec.withAlternative(Codec.doubleRange(MIN_GAMMA, MAX_GAMMA), Codec.BOOL, (boolean_) -> boolean_ ? MAX_GAMMA : MIN_GAMMA);
		}
	}
}

