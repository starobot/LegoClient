package net.staro.api;

import net.staro.lego.setting.GenericSetting;

import java.util.List;

public interface SettingManager {
    List<GenericSetting<?>> getSettings();

    GenericSetting<?> getSettingByName(String name);

}
