package net.staro.lego.api;

import net.staro.lego.api.trait.Nameable;

/**
 * A setting is an object that is linked to {@link Module}.
 * Settings can be created and registered for each module to set its values within the game.
 * @see SettingRegistry
 * @param <T>
 */
public interface Setting<T> extends Nameable {
    /**
     * Gets the value of the setting.
     * @return a generic T value.
     */
    T getValue();

    /**
     * A method used to set setting to a certain T value.
     * @param value a generic T value.
     */
    void setValue(T value);

    /**
     * Gets the default set value from the setting.
     * @return a generic default T value.
     */
    T getDefaultValue();

    /**
     * Gets the minimal value of the setting.
     * @return a generic minimal T value.
     */
    T getMin();

    /**
     * Gets the maximum value of the setting.
     * @return a generic maximum T value.
     */
    T getMax();

    String getDescription();

    /**
     * A method used to append a setting to a certain module.
     * @param module requires no explanation.
     */
    void setModule(Module module);

    /**
     * Get the setting value in the form of a String.
     * @return generic T type as a String.
     */
    String getType();

}
