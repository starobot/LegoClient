package net.staro.lego.api.trait;

import net.staro.lego.setting.type.Bind;

/**
 * A trait for something that can be bound.
 */
public interface Bindable {
    void setBind(int key);

    Bind getBind();

}
