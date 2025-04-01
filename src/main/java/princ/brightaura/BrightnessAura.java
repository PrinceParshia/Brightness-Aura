package princ.brightaura;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

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
			"key.night.vision",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_N,
			CATEGORY
	);

	@Override
	public void onInitializeClient() {
		registerBrightnessAuraKey();
		registerNightVisionKey();
	}

	public static final double MIN_GAMMA = 0.0, MAX_GAMMA = 15.0;
	public static final double GAMMA_SLIDER_INTERVAL = 0.01;
	private final int DEFAULT_GAMMA_TRANSITION_TIME = 20;
	private int GAMMA_TRANSITION_TIME;
	private double prevGamma;
	private double targetGamma;
	private boolean execGammaTransition;

	private void registerBrightnessAuraKey() {
		KeyBindingHelper.registerKeyBinding(brightnessAuraKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (brightnessAuraKey.consumeClick()) {
				if (!execGammaTransition) {
					targetGamma = (client.options.gamma().get() != MAX_GAMMA) ? MAX_GAMMA : prevGamma;
					if (targetGamma == MAX_GAMMA) prevGamma = client.options.gamma().get();
					GAMMA_TRANSITION_TIME = DEFAULT_GAMMA_TRANSITION_TIME;
					execGammaTransition = true;
				}
			}

			if (execGammaTransition) {
				if (GAMMA_TRANSITION_TIME <= 0) {
					client.options.gamma().set(targetGamma);
					execGammaTransition = false;
				} else {
					client.options.gamma().set(Mth.clamp(client.options.gamma().get() + ((targetGamma - client.options.gamma().get()) / GAMMA_TRANSITION_TIME), MIN_GAMMA, MAX_GAMMA));
					GAMMA_TRANSITION_TIME--;
				}
			}
		});
	}

	public static boolean isNightVisionEnabled;

	private void registerNightVisionKey() {
		KeyBindingHelper.registerKeyBinding(nightVisionKey);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (nightVisionKey.consumeClick()) {
				isNightVisionEnabled = !isNightVisionEnabled;
			}
		});
	}

	public enum UnitDouble implements OptionInstance.SliderableValueSet {
		INSTANCE;

		@Override
		public Optional<Double> validateValue(Double double_) {
			return double_ >= MIN_GAMMA && double_ <= MAX_GAMMA ? Optional.of(double_) : Optional.empty();
		}

		@Override
		public Double toSliderValue(Double double_) {
			return double_ / MAX_GAMMA;
		}

		@Override
		public Double fromSliderValue(double d) {
			return d * MAX_GAMMA;
		}

		@Override
		public Codec<Double> codec() {
			return Codec.withAlternative(Codec.doubleRange((MIN_GAMMA, MAX_GAMMA), Codec.BOOL, (boolean_) -> boolean_ ? MAX_GAMMA : MIN_GAMMA);
		}
	}
}
