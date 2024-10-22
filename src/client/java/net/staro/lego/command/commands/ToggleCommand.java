package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.staro.api.Module;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.ModuleArgument;

public class ToggleCommand extends LegoCommand {
    public ToggleCommand(Lego lego) {
        super(lego, "toggle", "Toggles modules on or off");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(
                argument("module", new ModuleArgument(lego))
                        .executes(context -> {
                            Module module = context.getArgument("module", Module.class);
                            module.toggle();
                            return COMPLETED;
                        }));
    }

}
