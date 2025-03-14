package princ.brightaura.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(OptionInstance.UnitDouble.class)
public class UnitDoubleMixin {
    @Inject(method = "validateValue", at = @At("HEAD"), cancellable = true)
    private void validateValue(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(
                double_ >= (double) 0.0F && double_ <= (double) 15.0F ? Optional.of(double_) : Optional.empty()
        );
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