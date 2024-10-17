package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

public class IntegerSetting extends GenericSetting<Integer> {
    public IntegerSetting(String name, int defaultValue, int minValue, int maxValue, String description) {
        super(name, defaultValue, minValue, maxValue, description);
    }

}
