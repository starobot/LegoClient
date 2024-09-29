package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.ConfigArgument;
import net.staro.lego.command.arguments.StringArgument;

public class ConfigCommand extends LegoCommand {
    private final ConfigArgument configArgument;

    public ConfigCommand(Lego lego) {
        super(lego, "config", "Manage configs");
        this.configArgument = new ConfigArgument(lego);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            lego.chat().sendLogless(Text.literal("The command usage is: config <save, load, del, list> <name>"));
            return COMPLETED;
        }).then(
                literal("save")
                        .then(argument("name", StringArgument.string())
                                .executes(context -> {
                                    String name = context.getArgument("name", String.class);
                                    lego.configManager().saveConfig(name, lego);
                                    lego.chat().sendLogless(Text.literal("")
                                            .append(Text.literal("Config by the name "))
                                            .append(Text.literal(name).formatted(Formatting.AQUA))
                                            .append(Text.literal(" has been created successfully")).formatted(Formatting.BOLD));
                                    return COMPLETED;
                                }))
        ).then(
                literal("load")
                        .then(argument("config", configArgument)
                                .executes(context -> {
                                    String config = context.getArgument("config", String.class);
                                    lego.configManager().loadConfig(config, lego);
                                    lego.chat().sendLogless(Text.literal("")
                                            .append(Text.literal("Config by the name "))
                                            .append(Text.literal(config).formatted(Formatting.AQUA))
                                            .append(Text.literal(" has been loaded successfully")).formatted(Formatting.BOLD));
                                    return COMPLETED;
                                }))
        )
                .then(
                        literal("del")
                                .then(argument("config", configArgument)
                                        .executes(context -> {
                                            String config = context.getArgument("config", String.class);
                                            lego.configManager().deleteConfig(config);
                                            lego.chat().sendLogless(Text.literal("")
                                                    .append(Text.literal("Config by the name "))
                                                    .append(Text.literal(config).formatted(Formatting.RED))
                                                    .append(Text.literal(" has been deleted successfully")).formatted(Formatting.BOLD));
                                            return COMPLETED;
                                        }))
                )
                .then(
                        literal("list")
                                .executes(context -> {
                                    var text = new StringBuilder();
                                    var configs = lego.configManager().getModuleConfigs();
                                    for (int i = 0; i < configs.size(); i++) {
                                        text.append(configs.get(i));
                                        if (i < configs.size() - 1) {
                                            text.append(", ");
                                        }
                                    }

                                    lego.chat().send(Text.literal("")
                                            .append(Text.literal("Config list: ").formatted(Formatting.BOLD).formatted(Formatting.AQUA))
                                            .append(Text.literal(text.toString()).formatted(Formatting.WHITE)));

                                    return COMPLETED;
                                })
                );
    }

}
