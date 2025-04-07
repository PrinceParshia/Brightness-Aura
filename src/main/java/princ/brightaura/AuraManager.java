package princ.brightaura;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class AuraManager {
    public static final double MIN_GAMMA = 0.0, MAX_GAMMA = 15.0;
    public static final double GAMMA_SLIDER_INTERVAL = 0.1;
    private static final int DEFAULT_GAMMA_TRANSITION_TIME = 20;
    private static int GAMMA_TRANSITION_TIME;
    private static double prevGamma;
    private static double targetGamma;
    private static int elapsedGammaTransitionTime;
    private static boolean gammaTransition;
    private static boolean execGammaTransition;

    public static void toggleGamma(boolean transition) {
        if (transition) {
            gammaTransition = true;
            if (!execGammaTransition) {
                targetGamma = (Minecraft.getInstance().options.gamma().get() != MAX_GAMMA) ? MAX_GAMMA : prevGamma;
                if (targetGamma == MAX_GAMMA) prevGamma = Minecraft.getInstance().options.gamma().get();
                GAMMA_TRANSITION_TIME = DEFAULT_GAMMA_TRANSITION_TIME;
                elapsedGammaTransitionTime = 0;
                execGammaTransition = true;
            } else {
                targetGamma = (targetGamma == MAX_GAMMA) ? prevGamma : MAX_GAMMA;
                GAMMA_TRANSITION_TIME = elapsedGammaTransitionTime;
                elapsedGammaTransitionTime = 0;
            }
        } else {
            gammaTransition = false;
            targetGamma = (Minecraft.getInstance().options.gamma().get() != MAX_GAMMA) ? MAX_GAMMA : prevGamma;
            Minecraft.getInstance().options.gamma().set(targetGamma);
        }
    }

    public static void handleGammaTransition() {
        if (gammaTransition) {
            if (execGammaTransition) {
                if (GAMMA_TRANSITION_TIME <= 0) {
                    Minecraft.getInstance().options.gamma().set(targetGamma);
                    execGammaTransition = false;
                    gammaTransition = false;
                } else {
                    Minecraft.getInstance().options.gamma().set(Mth.clamp(Minecraft.getInstance().options.gamma().get() + ((targetGamma - Minecraft.getInstance().options.gamma().get()) / GAMMA_TRANSITION_TIME), MIN_GAMMA, MAX_GAMMA));
                    GAMMA_TRANSITION_TIME--;
                    elapsedGammaTransitionTime++;
                }
            }
        }
    }

    public static boolean isFakeNightVisionEnabled;

    public static void toggleFakeNightVision() {
        isFakeNightVisionEnabled = !isFakeNightVisionEnabled;
    }
}