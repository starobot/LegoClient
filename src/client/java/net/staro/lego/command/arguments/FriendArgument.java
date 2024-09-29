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

public class FriendArgument implements ArgumentType<String> {
    private final Lego lego;

    public FriendArgument(Lego lego) {
        this.lego = lego;
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        var friend = reader.readString();
        if (!lego.friendManager().isFriend(friend)) {
            throw new DynamicCommandExceptionType(
                    name -> Text.literal("Friend with name " + name.toString() + " does not exist")
            ).create(friend);
        }

        return friend;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(lego.friendManager().getFriends(), builder);
    }

}
