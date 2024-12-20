package net.staro.lego.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.Getter;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.staro.api.Module;
import net.staro.api.trait.Nameable;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoArgument;

import java.util.concurrent.CompletableFuture;

@Getter
public class ModuleArgument extends LegoArgument<Module> {
    private static final DynamicCommandExceptionType NO_SUCH_MODULE = new DynamicCommandExceptionType(name ->
            Text.literal("There's no " + name + " try again."));

    public ModuleArgument(Lego lego) {
        super(lego);
    }

    @Override
    public Module parse(StringReader reader) throws CommandSyntaxException {
        var argument = reader.readString();
        var module = lego.moduleManager().getModuleByName(argument);
        if (module == null) {
            throw NO_SUCH_MODULE.create(argument);
        }

        return module;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(lego.moduleManager().getItems().stream().map(Nameable::getName), builder);
    }

}
