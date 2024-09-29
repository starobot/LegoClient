package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.StringArgument;

public class MessageCommand extends LegoCommand {
    public MessageCommand(Lego lego) {
        super(lego, "message", "Sends a server-side message in chat.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(
                argument("message", StringArgument.greedyString())
                        .executes(context -> {
                            String message = context.getArgument("message", String.class);
                            var player = mc.player;
                            if (player != null) {
                                lego.chat().sendPlayerMessage(message, player);
                            }

                            return COMPLETED;
                        }));
    }
}
