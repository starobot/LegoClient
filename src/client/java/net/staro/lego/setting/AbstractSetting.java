package net.staro.lego.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.staro.api.Setting;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractSetting<T> implements Setting<T> {
    protected final String name;
    protected final T defaultValue;

}
