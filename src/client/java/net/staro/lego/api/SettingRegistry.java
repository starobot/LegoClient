package net.staro.lego.api;

import net.staro.lego.setting.GenericSetting;

import java.util.List;

// TODO: this is not a registry anymore. rename and create additional methods.
public interface SettingRegistry {
    // TODO: This is redundant. Please remove it.
    default <T> Setting<T> register(GenericSetting<T> setting) {
        return setting;
    }

    List<GenericSetting<?>> getSettings();

    GenericSetting<?> getSettingByName(String name);

}
