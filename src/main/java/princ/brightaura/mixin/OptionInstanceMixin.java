package princ.brightaura.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(OptionInstance.class)
public class OptionInstanceMixin<T> {
    @Shadow
    @Final
    Component caption;

    @Shadow
    T value;

    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    private void codec(CallbackInfoReturnable<Codec<Double>> cir) {
        if (caption.getString().equals(I18n.get("options.gamma"))) {
            cir.setReturnValue(Codec.DOUBLE);
        }
    }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private void set(T object, CallbackInfo ci) {
        if (caption.getString().equals(I18n.get("options.gamma"))) {
            double double_ = ((Number) object).doubleValue();
            Optional<Double> validateValue = double_ >= (double) 0.0F && double_ <= (double) 15.0F ? Optional.of(double_) : Optional.empty();

            if (validateValue.isPresent()) {
                this.value = object;
            } else {
                System.out.println("Illegal option value: " + String.valueOf(object) + " for " + String.valueOf(this.caption) );
            }

            ci.cancel();
        }
    }
}