package net.staro.lego.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.api.Module;
import net.staro.api.trait.Nameable;
import net.staro.lego.setting.GenericSetting;

import java.util.concurrent.CompletableFuture;

public class SettingArgument implements ArgumentType<String> {
    private final Module module;
    private static final DynamicCommandExceptionType NO_SUCH_SETTING =
            new DynamicCommandExceptionType(name -> Text.literal("A setting called '")
                    .append(name.toString()).formatted(Formatting.RED)
                    .append("' doesn't exist."));

    public SettingArgument(Module module) {
        this.module = module;
    }

    public GenericSetting<?> get(CommandContext<?> context) throws CommandSyntaxException {
        var settingName = context.getArgument("setting", String.class);

        GenericSetting<?> setting = module.getSettingByName(settingName);
        if (setting == null) {
            throw NO_SUCH_SETTING.create(settingName);
        }

        return setting;
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(module.getSettings().stream().map(Nameable::getName), builder);

    }

}
