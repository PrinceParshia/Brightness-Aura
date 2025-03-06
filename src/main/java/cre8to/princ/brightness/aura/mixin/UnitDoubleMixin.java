package cre8to.princ.brightness.aura.mixin;

import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

@Mixin(OptionInstance.UnitDouble.class)
public abstract class UnitDoubleMixin {
    @Inject(
            method = {"validateValue(Ljava/lang/Double;)Ljava/util/Optional;"},
            at = {@At("RETURN")},
            cancellable = true
    )
    private void unLimitValidateValues(Double value, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(Optional.of(value));
    }
}