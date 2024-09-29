package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;

public class ModulesCommand extends LegoCommand {
    public ModulesCommand(Lego lego) {
        super(lego, "modules", "Shows available modules");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            lego.chat().sendLogless(Text.of(Formatting.GRAY + "Modules: "));
            lego.moduleManager().getCategories().forEach(category -> {
                var categoryText = Text.literal(category.getName() + ": ").formatted(Formatting.GRAY);
                var modulesText = lego.moduleManager().getModulesByCategory(category).stream()
                        .map(module -> Text.literal(module.getName())
                                .formatted(module.isEnabled() ? Formatting.AQUA : Formatting.RED)
                                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        Text.literal(module.getDescription())))))
                        .reduce((text1, text2) -> text1.append(Text.literal(", ")).append(text2))
                        .orElse(Text.literal(""));
                lego.chat().send(categoryText.append(modulesText));
            });
            return COMPLETED;
        });
    }

}
