package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.StringArgument;

public class PrefixCommand extends LegoCommand {
    public PrefixCommand(Lego lego) {
        super(lego, "prefix", "Sets up the prefix for the client.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(
                argument("prefix", StringArgument.string())
                        .executes(context -> {
                            var prefix = context.getArgument("prefix", String.class);
                            lego.commandManager().setPrefix(prefix);
                            lego.chat().send(Text.literal("The prefix is set to ").formatted(Formatting.BOLD)
                                    .append(Text.literal(prefix).formatted(Formatting.BOLD).formatted(Formatting.RED)));
                            return COMPLETED;
                        }));
    }

}
