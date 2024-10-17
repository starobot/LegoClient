package net.staro.lego.api;

import net.staro.lego.setting.type.Bind;

import java.awt.*;

/**
 * A factory class that handles creating new setting objects.
 * @see Setting
 * @see SettingManager
 */
public interface SettingFactory extends SettingManager {
    /**
     * Creates and registers a new Setting object with the boolean value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @return a new Setting object with the Boolean value.
     */
    Setting<Boolean> bool(String name, boolean defaultValue, String description);

    /**
     * Creates and registers a new Setting object with the Integer value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @param minValue is the minimum value of the setting.
     * @param maxValue is the maximum value of the setting.
     * @return a new Setting object with the Integer value.
     */
    Setting<Integer> integer(String name, int defaultValue, int minValue, int maxValue, String description);

    /**
     * Creates and registers a new Setting object with the Float value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @param minValue is the minimum value of the setting.
     * @param maxValue is the maximum value of the setting.
     * @return a new Setting object with the Float value.
     */
    Setting<Float> floating(String name, float defaultValue, float minValue, float maxValue, String description);

    /**
     * Creates and registers a new Setting object with the Double value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @param minValue is the minimum value of the setting.
     * @param maxValue is the maximum value of the setting.
     * @return a new Setting object with the Double value.
     */
    Setting<Double> precise(String name, double defaultValue, double minValue, double maxValue, String description);

    /**
     * Creates and registers a new Setting object with the Enum value.
     * @param name is the name of the setting.
     * @param defaultValue is the default enum value of the setting.
     * @return a new Setting object with the Enum value.
     */
    <E extends Enum<E>> Setting<E> enumSetting(String name, E defaultValue, String description);

    /**
     * Creates and registers a new Setting object with the String value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @return a new Setting object with the String value.
     */
    Setting<String> string(String name, String defaultValue, String description);

    /**
     * Creates and registers a new Setting object with the Color value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @return a new Setting object with the Color value.
     */
    Setting<Color> color(String name, Color defaultValue, String description);

    /**
     * Creates and registers a new Setting object with the {@link Bind} value.
     * @param name is the name of the setting.
     * @param defaultValue is the default value of the setting.
     * @return a new Setting object with the {@link Bind} value.
     */
    Setting<Bind> bind(String name, Bind defaultValue, String description);

}
