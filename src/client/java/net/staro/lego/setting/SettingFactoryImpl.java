package net.staro.lego.setting;

import net.staro.api.Setting;
import net.staro.api.SettingFactory;
import net.staro.lego.setting.settings.*;
import net.staro.lego.setting.type.Bind;

import java.awt.*;

public abstract class SettingFactoryImpl implements SettingFactory {
    @Override
    public Setting<Boolean> bool(String name, boolean defaultValue, String description) {
        return new BooleanSetting(name, defaultValue, description);
    }

    @Override
    public Setting<Integer> integer(String name, int defaultValue, int minValue, int maxValue, String description) {
        return new IntegerSetting(name, defaultValue, minValue, maxValue, description);
    }

    @Override
    public Setting<Float> floating(String name, float defaultValue, float minValue, float maxValue, String description) {
        return new FloatSetting(name, defaultValue, minValue, maxValue, description);
    }

    @Override
    public Setting<Double> precise(String name, double defaultValue, double minValue, double maxValue, String description) {
        return new DoubleSetting(name, defaultValue, minValue, maxValue, description);
    }

    @Override
    public <E extends Enum<E>> Setting<E> enumSetting(String name, E defaultValue, String description) {
        return new GenericSetting<>(name, defaultValue, description);
    }

    @Override
    public Setting<String> string(String name, String defaultValue, String description) {
        return new StringSetting(name, defaultValue, description);
    }

    @Override
    public Setting<Color> color(String name, Color defaultValue, String description) {
        return new ColorSetting(name, defaultValue, description);
    }

    @Override
    public Setting<Bind> bind(String name, Bind defaultValue, String description) {
        return new BindSetting(name, defaultValue, description);
    }

}
