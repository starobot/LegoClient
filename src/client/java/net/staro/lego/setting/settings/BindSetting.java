package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;
import net.staro.lego.setting.type.Bind;

public class BindSetting extends GenericSetting<Bind> {
    public BindSetting(String name, Bind defaultValue, String description) {
        super(name, defaultValue, description);
    }

}
