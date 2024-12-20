package net.staro.lego.command.commands;

import com.google.gson.JsonParser;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.command.CommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.api.Module;
import net.staro.api.Setting;
import net.staro.lego.Lego;
import net.staro.lego.command.Chat;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.SettingArgument;
import net.staro.lego.command.arguments.SettingValueArgument;
import net.staro.lego.setting.GenericSetting;
import net.staro.lego.utility.SettingUtil;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class ModuleCommand extends LegoCommand {
    private final Module module;
    private final SettingArgument settingArgument;
    private final SettingValueArgument settingValueArgument;
    private final TriConsumer<Setting<?>, String, Module> setEnabledModule = (setting, settingValue, module) -> {
        if (setting.getName().equalsIgnoreCase("Enabled")) {
            if (settingValue.equalsIgnoreCase("true")) {
                module.enable();
            } else if (settingValue.equalsIgnoreCase("false")) {
                module.disable();
            }
        }
    };

    public ModuleCommand(Module module, Lego lego, String name, String description) {
        super(lego, name, description);
        this.module = module;
        this.settingArgument = new SettingArgument(module);
        this.settingValueArgument = new SettingValueArgument(settingArgument);
    }

    @Override
    @SuppressWarnings({"rawtypes"})
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        Chat chat = lego.chat();
        builder.then(argument("setting", settingArgument).executes(context -> {
            chat.send("nope");
            return COMPLETED;
        }).then(argument("value", settingValueArgument).executes(context -> {
            Setting setting = settingArgument.get(context);
            var settingValue = context.getArgument("value", String.class);
            if (setting.getName().equalsIgnoreCase("Bind")) {
                try {
                    module.setBind(SettingUtil.convertToBind(settingValue.toUpperCase()).getKey());
                } catch (Exception e) {
                    chat.send(Text.literal("")
                            .append(Text.literal("Bad value! There's no key "))
                            .append(Text.literal(settingValue).formatted(Formatting.RED)));
                    return COMPLETED;
                }
            } else {
                try {
                    setEnabledModule.accept(setting, settingValue, module);
                    SettingUtil.setCommandValue(module, setting, JsonParser.parseString(settingValue));
                } catch (Exception e) {
                    chat.send(Text.literal("Bad Value! This setting requires a: " + setting.getType() + " value."));
                    return COMPLETED;
                }
            }

            chat.send(Text.literal("")
                    .append(Text.literal(module.getName()).formatted(Formatting.BOLD).styled(style ->
                            style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal(module.getDescription())))))
                    .append(Text.literal(" " + setting.getName() + " has been set to ").formatted(Formatting.GRAY))
                    .append(Text.literal(settingValue)));
            return COMPLETED;
        })));
        builder.executes(context -> {
            chat.send(Text.literal((module.isEnabled() ? Formatting.AQUA : Formatting.RED) + module.getName() + ": ")
                    .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Text.literal(module.getDescription())))));
            List<GenericSetting<?>> filteredSettings = module.getSettings().stream()
                    .filter(setting -> {
                        Predicate<String> settingName = name -> setting.getName().equalsIgnoreCase(name);
                        return !(settingName.test("Bind") || settingName.test("Drawn"));
                    }).toList();
            filteredSettings.forEach(setting -> {
                String value = setting.getValue().toString();
                Formatting valueColor = Formatting.WHITE;
                if (setting.getType().equalsIgnoreCase("Boolean") && value.equalsIgnoreCase("false")) {
                    valueColor = Formatting.OBFUSCATED;
                }

                var settingName = Text.literal(setting.getName()).formatted(Formatting.GRAY);
                settingName = settingName.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal(setting.getDescription()))));
                chat.send(Text.literal("")
                        .append(settingName)
                        .append(Text.literal(": "))
                        .append(Text.literal(value).formatted(valueColor)));
            });
            return COMPLETED;
        });
    }

}
