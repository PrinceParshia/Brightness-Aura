package cre8to.princ.brightness.aura.mixin;

import cre8to.princ.brightness.aura.BrightnessAura;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Options.class})
public abstract class OptionsMixin {
    @Final
    @Shadow
    private OptionInstance<Double> gamma;

    @Inject(
            method = {"gamma"},
            at = {@At("RETURN")},
            cancellable = true
    )
    private void onLoad(CallbackInfoReturnable<OptionInstance<Double>> info) {
        if (BrightnessAura.isBrightnessMax()) {
            if (gamma.get() != 15.0) {
                gamma.set(15.0);
            }
        } else if (gamma.get() == 15.0) {
            gamma.set(1.0);
        }
    }
}