package princ.brightaura.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.brightaura.AuraManager.isFakeNightVisionEnabled;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hasEffect", at=@At("HEAD"), cancellable = true)
    private void fakeHasEffect(Holder<MobEffect> holder, CallbackInfoReturnable<Boolean> cir) {
        if (holder == MobEffects.NIGHT_VISION && isFakeNightVisionEnabled) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getEffect", at=@At("HEAD"), cancellable = true)
    private void fakeGetEffect(Holder<MobEffect> holder, CallbackInfoReturnable<MobEffectInstance> cir) {
        if (holder == MobEffects.NIGHT_VISION && isFakeNightVisionEnabled) {
            cir.setReturnValue(new MobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION));
        }
    }
}