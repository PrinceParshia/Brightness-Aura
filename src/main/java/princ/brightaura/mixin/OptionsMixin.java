package princ.brightaura.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import princ.brightaura.BrightnessAura;

import static net.minecraft.client.Options.genericValueLabel;

@Mixin(Options.class)
public class OptionsMixin {
    @Mutable
    @Shadow
    @Final
    private OptionInstance<Double> gamma;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.gamma = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), (component, double_) -> {
            int i = (int) (double_ * (double) 100.0F);
            if (i == 0) {
                return genericValueLabel(component, Component.translatable("options.gamma.min"));
            } else if (i == 50) {
                return genericValueLabel(component, Component.translatable("options.gamma.default"));
            } else {
                return i == 100 ? genericValueLabel(component, Component.translatable("options.gamma.max")) : genericValueLabel(component, i);
            }
        }, BrightnessAura.UnitDouble.INSTANCE, (double) 0.5F, (double_) -> {
        });
    }
}
