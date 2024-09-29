package net.staro.lego.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.minecraft.command.CommandSource;
import net.staro.lego.setting.GenericSetting;
import net.staro.lego.setting.converter.EnumConverter;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SettingValueArgument implements ArgumentType<String> {
    private final SettingArgument settingArgument;

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    @Override
    @SneakyThrows   // This is terrible, but i cba to make proper suggestions.
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        GenericSetting<?> setting = settingArgument.get(context);
        List<String> suggestions = switch (setting.getType()) {
            case "Boolean", "Enabled" -> Arrays.asList("true", "false");
            case "Integer" -> generateNumberSuggestions((Integer) setting.getMin(), (Integer) setting.getValue(), (Integer) setting.getMax());
            case "Double" -> generateNumberSuggestions((Double) setting.getMin(), (Double) setting.getValue(), (Double) setting.getMax());
            case "Float" -> generateNumberSuggestions((Float) setting.getMin(), (Float) setting.getValue(), (Float) setting.getMax());
            case "Color" -> generateColorSuggestion((Color) setting.getValue());
            case "Enum" -> {
                Enum<?> enumValue = (Enum<?>) setting.getValue();
                yield  Arrays.stream(EnumConverter.getNames(enumValue)).collect(Collectors.toList());
            }
            case "Bind" -> BindArgument.ALL_KEY_CODES.stream()
                    .map(keyCode -> {
                        String kn = keyCode > 0 ? GLFW.glfwGetKeyName(keyCode, GLFW.glfwGetKeyScancode(keyCode)) : "None";
                        if (kn == null) {
                            try {
                                for (Field declaredField : GLFW.class.getDeclaredFields()) {
                                    if (declaredField.getName().startsWith("GLFW_KEY_")) {
                                        int a = (int) declaredField.get(null);

                                        if (a == keyCode) {
                                            String nb = declaredField.getName().substring("GLFW_KEY_".length());
                                            kn = nb.substring(0, 1).toUpperCase() + nb.substring(1).toLowerCase();
                                        }
                                    }
                                }
                            } catch (Exception
                                    ignore) {
                                kn = "unknown." + keyCode;
                            }
                        }
                        return keyCode == -1 ? "None" : Objects.requireNonNull(kn).toUpperCase();
                    })
                    .toList();
            default -> List.of("");
        };

        return CommandSource.suggestMatching(suggestions, builder);
    }

    private List<String> generateNumberSuggestions(Number min, Number current ,Number max) {
        return Arrays.asList(min.toString(), current.toString(), max.toString());
    }

    private List<String> generateColorSuggestion(Color color) {
        return Collections.singletonList(Integer.toHexString(color.getRGB()).substring(2).toUpperCase());
    }

}
