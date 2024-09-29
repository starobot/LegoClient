package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;

public class QuitCommand extends LegoCommand {
    public QuitCommand(Lego lego) {
        super(lego, "quit", "Closes minecraft instance");
    }

    @Override
    public void build(Lego lego, LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            mc.close();
            return COMPLETED;
        });
    }

}
