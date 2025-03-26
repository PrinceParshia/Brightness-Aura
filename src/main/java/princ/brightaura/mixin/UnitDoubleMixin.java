package princ.brightaura.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import princ.brightaura.BrightnessAura;

import java.util.Optional;

@Mixin(OptionInstance.UnitDouble.class)
public class UnitDoubleMixin {
    @Inject(method = "validateValue", at = @At("HEAD"), cancellable = true)
    private void validateValue(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(
                double_ >= (double) 0.0F && double_ <= (double) 15.0F ? Optional.of(double_) : Optional.empty()
        );
    }

    @Inject(method = "toSliderValue", at = @At("HEAD"), cancellable = true)
    private void toSliderValue(Double double_, CallbackInfoReturnable<Double> cir) {
        if (((TranslatableContents) Component.translatable("options.gamma").getContents()).getKey().equals("options.gamma")) { return; }
        cir.setReturnValue(double_ / BrightnessAura.MAX_GAMMA);

    }

    @Inject(method = "fromSliderValue", at = @At("HEAD"), cancellable = true)
    private void fromSliderValue(double d, CallbackInfoReturnable<Double> cir) {
        if (((TranslatableContents) Component.translatable("options.gamma").getContents()).getKey().equals("options.gamma")) { return; }
        cir.setReturnValue(d * BrightnessAura.MAX_GAMMA);

    }

    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    private void codec(CallbackInfoReturnable<Codec<Double>> cir) {
        Codec<Double> codec = Codec.withAlternative(
                Codec.doubleRange((double) 0.0F, (double) 15.0F),
                Codec.BOOL,
                (boolean_) -> boolean_ ? (double) 15.0F : (double) 0.0F
        );

        cir.setReturnValue(codec);
    }
}