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
import net.staro.lego.Lego;

import java.util.concurrent.CompletableFuture;

public class ConfigArgument implements ArgumentType<String> {
    private final Lego lego;

    public ConfigArgument(Lego lego) {
        this.lego = lego;
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        var config = reader.readString();
        if (!lego.configManager().getModuleConfigs().contains(config)) {
            throw new DynamicCommandExceptionType(
                    name -> Text.literal("Config with name " + name.toString() + " does not exist")
            ).create(config);
        }

        return config;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(lego.configManager().getModuleConfigs(), builder);
    }

}
