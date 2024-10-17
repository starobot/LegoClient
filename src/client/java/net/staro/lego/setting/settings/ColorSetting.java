package net.staro.lego.setting.settings;

import net.staro.lego.setting.GenericSetting;

import java.awt.*;

public class ColorSetting extends GenericSetting<Color> {
    public ColorSetting(String name, Color defaultValue, String description) {
        super(name, defaultValue, description);
    }

}
