package net.staro.lego.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.staro.lego.setting.type.Bind;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BindArgument implements ArgumentType<Bind> {
    public static final List<Integer> ALL_KEY_CODES = Arrays.stream(GLFW.class.getDeclaredFields())
            .filter(field -> field.getName().startsWith("GLFW_KEY_"))
            .map(field -> {
                try {
                    return field.getInt(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e); // This should never happen, I think, but whatever.
                }
            })
            .toList();

    @Override
    public Bind parse(StringReader reader) throws CommandSyntaxException {
        String keyName = reader.readString().toUpperCase();
        Optional<Integer> matchingKeyCode = ALL_KEY_CODES.stream()
                .filter(keyCode -> {
                    String glfwKeyName = GLFW.glfwGetKeyName(keyCode, 0);
                    if (glfwKeyName != null && glfwKeyName.startsWith("GLFW_KEY_")) {
                        glfwKeyName = glfwKeyName.substring("GLFW_KEY_".length());
                    }
                    return glfwKeyName != null && glfwKeyName.toUpperCase().equals(keyName);
                })
                .findFirst();
        if (matchingKeyCode.isPresent()) {
            return new Bind(matchingKeyCode.get());
        } else {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(ALL_KEY_CODES.stream().map(this::getKeys).toList(), builder);
    }

    private String getKeys(int keyCode) {
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
            } catch (Exception ignore) {
                kn = "unknown." + keyCode;
            }
        }

        return keyCode == -1 ? "None" : Objects.requireNonNull(kn).toUpperCase();
    }

}
