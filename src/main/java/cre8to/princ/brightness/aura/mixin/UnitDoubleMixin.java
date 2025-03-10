package cre8to.princ.brightness.aura.mixin;

import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(OptionInstance.UnitDouble.class)
public abstract class UnitDoubleMixin<T> {
    @Overwrite
    public Optional<T> validateValue(T object) {
        return Optional.of(object);
    }
}