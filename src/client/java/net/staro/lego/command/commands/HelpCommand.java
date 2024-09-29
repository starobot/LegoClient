package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;

public class HelpCommand extends LegoCommand {
    public HelpCommand(Lego lego) {
        super(lego, "help", "Shows the list of available commands.");
    }

    @Override
    public void build(Lego lego, LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            lego.chat().sendLogless(Text.literal("Available commands:"));
            lego.commandManager().getCommands().forEach(command -> {
                var commandText = Text.literal(command.getName());
                lego.chat().send(commandText.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal(command.getDescription())))));
            });
            return COMPLETED;
        });
    }

}
