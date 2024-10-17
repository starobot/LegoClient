package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

public class StringSetting extends GenericSetting<String> {
    public StringSetting(String name, String defaultValue, String description) {
        super(name, defaultValue, description);
    }

}
