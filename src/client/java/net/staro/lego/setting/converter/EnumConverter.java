package net.staro.lego.setting.converter;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class EnumConverter extends Converter<Enum, JsonElement> {
    private final Class<? extends Enum> clazz;

    public EnumConverter(Class<? extends Enum> clazz) {
        this.clazz = clazz;
    }

    public static int currentEnum(Enum<?> clazz) {
        return Arrays.stream(clazz.getClass().getEnumConstants())
                .map(Enum::name)
                .toList()
                .indexOf(clazz.name());
    }

    public static Enum<?> increaseEnum(Enum<?> clazz) {
        Enum<?>[] constants = clazz.getClass().getEnumConstants();
        int index = currentEnum(clazz);
        if (index >= 0 && index < constants.length - 1) {
            return constants[index + 1];
        }
        return constants[0];
    }

    public static Enum<?> setEnumInt(@NotNull Enum<?> clazz, int id) {
        Enum<?>[] constants = clazz.getClass().getEnumConstants();
        if (id >= 0 && id < constants.length) {
            return constants[id];
        }
        return constants[0];
    }

    public static String getProperName(Enum clazz) {
        return Character.toUpperCase(clazz.name().charAt(0)) + clazz.name().toLowerCase().substring(1);
    }

    public @NotNull JsonElement doForward(Enum anEnum) {
        return new JsonPrimitive(anEnum.toString());
    }

    public @NotNull Enum doBackward(JsonElement jsonElement) {
        try {
            return Enum.valueOf(this.clazz, jsonElement.getAsString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String[] getNames(Enum clazz) {
        return Arrays.stream(clazz.getClass().getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

}

