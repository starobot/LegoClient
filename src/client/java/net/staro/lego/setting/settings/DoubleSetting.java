package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

public class DoubleSetting extends GenericSetting<Double> {
    public DoubleSetting(String name, double defaultValue, double minValue, double maxValue, String description) {
        super(name, defaultValue, minValue, maxValue, description);
    }

}
