package net.staro.lego.utility;

import com.google.gson.JsonElement;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.staro.lego.api.Module;
import net.staro.lego.api.Setting;
import net.staro.lego.command.arguments.BindArgument;
import net.staro.lego.setting.GenericSetting;
import net.staro.lego.setting.converter.EnumConverter;
import net.staro.lego.setting.type.Bind;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UtilityClass
public class SettingUtil {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setCommandValue(Module module, Setting<?> setting, JsonElement element) {
        for (GenericSetting checkSetting : module.getSettings()) {
            if (Objects.equals(setting.getName(), checkSetting.getName())) {
                switch (checkSetting.getType()) {
                    case "Parent" -> {
                        return;
                    }
                    case "Boolean" -> {
                        checkSetting.setValue(element.getAsBoolean());
                        return;
                    }
                    case "Double" -> {
                        checkSetting.setValue(element.getAsDouble());
                        return;
                    }
                    case "Float" -> {
                        checkSetting.setValue(element.getAsFloat());
                        return;
                    }
                    case "Integer" -> {
                        checkSetting.setValue(element.getAsInt());
                        return;
                    }
                    case "String" -> {
                        var string = element.getAsString();
                        checkSetting.setValue(string.replace("_", " "));
                        return;
                    }
                    case "Color" -> {
                        try {
                            Color color = ColorUtil.hexToColor(element.getAsString());
                            checkSetting.setValue(color);
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Invalid color code.");
                        }
                        return;
                    }
                    case "Enum" -> {
                        try {
                            EnumConverter converter = new EnumConverter(((Enum<?>) checkSetting.getValue()).getClass());
                            Enum<?> value = converter.doBackward(element);
                            checkSetting.setValue(value);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    // Oyvey technologies.
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setValueFromJson(Module feature, Setting setting, JsonElement element) {
        String str;
        switch (setting.getType()) {
            case "Boolean" -> {
                setting.setValue(element.getAsBoolean());
            }
            case "Double" -> {
                setting.setValue(element.getAsDouble());
            }
            case "Float" -> {
                setting.setValue(element.getAsFloat());
            }
            case "Integer" -> {
                setting.setValue(element.getAsInt());
            }
            case "String" -> {
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
            }
            case "Bind" -> {
                setting.setValue(new Bind(element.getAsInt()));
            }
            case "Enum" -> {
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(element);
                    setting.setValue(value);
                } catch (Exception ignored) {
                }
            }
            case "Color" -> {
                try {
                    Color color = ColorUtil.hexToColor(element.getAsString());
                    setting.setValue(color);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid color code.");
                }
            }
            default -> {
                log.error("Unknown Setting type for: {} : {}", feature.getName(), setting.getName());
            }
        }
    }

    public Bind convertToBind(String key) {
        Optional<Integer> matchingKeyCode = BindArgument.ALL_KEY_CODES.stream()
                .filter(keyCode -> {
                    String glfwKeyName = GLFW.glfwGetKeyName(keyCode, 0);
                    if (glfwKeyName != null && glfwKeyName.startsWith("GLFW_KEY_")) {
                        glfwKeyName = glfwKeyName.substring("GLFW_KEY_".length());
                    }
                    return glfwKeyName != null && glfwKeyName.toUpperCase().equals(key);
                })
                .findFirst();
        if (matchingKeyCode.isPresent()) {
            return new Bind(matchingKeyCode.get());
        } else {
            throw new RuntimeException();
        }
    }

}
