package princ.brightaura.mixin;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static princ.brightaura.AuraManager.*;

@Pseudo
@Mixin(SliderControl.class)
public class SliderControlMixin {
    @Shadow
    @Final
    @Mutable
    private int min;

    @Shadow
    @Final
    @Mutable
    private int max;

    @Shadow
    @Final
    @Mutable
    private int interval;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode, CallbackInfo info) {
        if (option.getName().getContents() instanceof TranslatableContents content && content.getKey().equals("options.gamma")) {
            this.min = (int) (MIN_GAMMA);
            this.max = (int) (MAX_GAMMA * 100);
            this.interval = (int) (GAMMA_SLIDER_INTERVAL * 100);
        }
    }
}