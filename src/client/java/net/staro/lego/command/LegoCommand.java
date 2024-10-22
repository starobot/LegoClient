package net.staro.lego.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.staro.api.Command;
import net.staro.lego.Lego;

@Getter
public abstract class LegoCommand implements Command {
    protected final Lego lego;
    private final String name;
    private final String description;
    protected final MinecraftClient mc;

    protected LegoCommand(Lego lego, String name, String description) {
        this.lego = lego;
        this.name = name;
        this.description = description;
        mc = lego.mc();
    }

    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected static LiteralArgumentBuilder<CommandSource> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        build(builder);
        dispatcher.register(builder);
    }

}
