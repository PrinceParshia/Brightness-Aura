package princ.brightaura;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;

import java.util.Optional;

public enum Idk implements OptionInstance.SliderableValueSet {
    INSTANCE;

    @Override
    public double toSliderValue(Object object) {
        return 0;
    }

    @Override
    public Object fromSliderValue(double d) {
        return null;
    }

    @Override
    public Optional validateValue(Object object) {
        return Optional.empty();
    }

    @Override
    public Codec codec() {
        return null;
    }
}
