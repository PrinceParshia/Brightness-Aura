package princ.brightaura.mixin;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import princ.brightaura.BrightnessAura;

@Pseudo
@Mixin(SliderControl.class)
public class SliderControlMixin {
    @Mutable
    @Shadow
    @Final
    private int min;

    @Mutable
    @Shadow
    @Final
    private int max;

    @Mutable
    @Shadow
    @Final
    private int interval;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode, CallbackInfo info) {
        if (option.getName().getContents() instanceof TranslatableContents content && content.getKey().equals("options.gamma")) {
            this.min = (int) (0);
            this.max = (int) (BrightnessAura.maxGamma * 100);
            this.interval = (int) (0.05 * 100);
        }
    }
}