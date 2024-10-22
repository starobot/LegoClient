package net.staro.lego.setting;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.staro.api.Module;
import net.staro.lego.setting.type.Bind;

import java.awt.*;

@Getter
@Setter
public class GenericSetting<T> extends AbstractSetting<T> {
    private final String name;
    private T defaultValue;
    private T value;
    private T min;
    private T max;
    private boolean hasRestriction;
    private T plannedValue;
    private final String description;

    private Module module;

    public GenericSetting(String name, T defaultValue, String description) {
        super(name, defaultValue);
        this.name = name;
        this.value = defaultValue;
        this.description = description;
    }

    public GenericSetting(String name, T defaultValue, T min, T max, String description) {
        super(name, defaultValue);
        this.name = name;
        this.min = min;
        this.max = max;
        this.value = defaultValue;
        this.hasRestriction = true;
        this.description = description;
    }

    @Override
    public void setValue(T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number) this.min).floatValue() > ((Number) value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number) this.max).floatValue() < ((Number) value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }

        this.value = this.plannedValue;
    }

    @Override
    @NonNull
    public String getType() {
        if (this.isEnumSetting()) {
            return "Enum";
        }

        return this.getClassName(this.value);
    }

    public boolean isEnumSetting() {
        return !this.isNumberSetting()
                && !(this.value instanceof String)
                && !(this.value instanceof Bind)
                && !(this.value instanceof Character)
                && !(this.value instanceof Boolean)
                && !(this.value instanceof Color);
    }

    private boolean isNumberSetting() {
        return this.value instanceof Double
                || this.value instanceof Integer
                || this.value instanceof Short
                || this.value instanceof Long
                || this.value instanceof Float;
    }

    @SuppressWarnings("TypeParameterHidesVisibleType")
    private <T> String getClassName(T value) {
        return value.getClass().getSimpleName();
    }

}
