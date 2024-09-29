package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.api.Module;
import net.staro.lego.command.Chat;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.BindArgument;
import net.staro.lego.command.arguments.ModuleArgument;
import net.staro.lego.setting.type.Bind;
import org.lwjgl.glfw.GLFW;

public class BindCommand extends LegoCommand {
    private final BindArgument bindArgument;

    public BindCommand(Lego lego) {
        super(lego, "bind", "Sets the bind of the module");
        bindArgument = new BindArgument();
    }

    @Override
    public void build(Lego lego, LiteralArgumentBuilder<CommandSource> builder) {
        Chat chat = lego.chat();
        builder.then(
                argument("module", new ModuleArgument(lego)).executes(context -> {
                    Module module = context.getArgument("module", Module.class);
                    var keyboardService = lego.serviceManager().getKeyboardService();
                    if (module.getBind().getKey() != -1) {
                        chat.sendLogless(Text.literal("Current bind for "
                                + module.getName()
                                + ": "
                                + GLFW.glfwGetKeyName(
                                module.getBind().getKey(), GLFW.glfwGetKeyScancode(module.getBind()
                                        .getKey()))).formatted(Formatting.DARK_GRAY));
                    }

                    chat.sendLogless(Text.literal("Press the Key").formatted(Formatting.GRAY));
                    keyboardService.setModule(module);
                    keyboardService.getListening().set(true);
                    return COMPLETED;
                }).then(
                        argument("key", bindArgument)
                                .executes(context -> {
                                    Module module = context.getArgument("module", Module.class);
                                    var key = context.getArgument("key", Bind.class);
                                    if (key.isEmpty()) {
                                        chat.sendLogless(Text.literal(
                                                "The bind for" + module.getName().formatted(Formatting.WHITE).formatted(Formatting.BOLD)
                                                        + " is currently set to " + module.getBind().getBindName()).formatted(Formatting.GRAY));
                                    }

                                    module.setBind(key.getKey());
                                    chat.sendLogless(Text.literal(
                                            "The bind for " + module.getName().formatted(Formatting.WHITE).formatted(Formatting.BOLD)
                                                    + " has been set to " + module.getBind().getBindName()).formatted(Formatting.GRAY));
                                    return COMPLETED;
                                }))
        );
    }

}
