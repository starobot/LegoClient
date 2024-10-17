package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

public class FloatSetting extends GenericSetting<Float> {
    public FloatSetting(String name, float defaultValue, float minValue, float maxValue, String description) {
        super(name, defaultValue, minValue, maxValue, description);
    }

}
