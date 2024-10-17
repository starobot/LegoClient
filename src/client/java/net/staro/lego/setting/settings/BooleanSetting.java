package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

public class BooleanSetting extends GenericSetting<Boolean> {
    public BooleanSetting(String name, Boolean defaultValue, String description) {
        super(name, defaultValue, description);
    }

}
