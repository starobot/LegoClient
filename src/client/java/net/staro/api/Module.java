package net.staro.api;

import net.staro.api.trait.*;
import net.staro.lego.module.Category;

/**
 * Represents a module which is the main functional part of the client.
 * Modules are object that could be toggled, bound and written intoJson. Also, modules are the only objects to have settings withing them.
 */
public interface Module extends
        Toggleable,
        Nameable,
        HasDescription,
        SettingFactory,
        Jsonable,
        Bindable
{
    /**
     * Gets the category of the module.
     * @return a category.
     */
    Category getCategory();

    /**
     * Checks if the module is drawn (visible).
     * @return true if drawn and false otherwise.
     */
    boolean isDrawn();

    /**
     * A method that sets the drawn state to true of false
     * @param state true if drawn and false otherwise.
     */
    void drawn(boolean state);

}
