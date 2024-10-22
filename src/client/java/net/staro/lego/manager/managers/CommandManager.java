package net.staro.lego.manager.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.staro.api.Command;
import net.staro.api.trait.Jsonable;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.commands.*;
import net.staro.lego.manager.GenericManager;

import java.util.List;

@Getter
public class CommandManager extends GenericManager<Command> implements Jsonable {
    @Setter
    private String prefix = "+";
    private final String configName = "prefix.json";
    protected final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
    private final CommandSource commandSource = new ClientCommandSource(null, MinecraftClient.getInstance());

    @Override
    public void initialize(Lego lego) {
        registerCommand(new HelpCommand(lego));
        registerCommand(new ToggleCommand(lego));
        registerCommand(new ModulesCommand(lego));
        registerCommand(new QuitCommand(lego));
        registerCommand(new MessageCommand(lego));
        registerCommand(new PrefixCommand(lego));
        registerCommand(new FriendCommand(lego));
        registerCommand(new BindCommand(lego));
        registerCommand(new ConfigCommand(lego));
        lego.moduleManager().getItems().forEach(module ->
                registerCommand(new ModuleCommand(module, lego, module.getName(), module.getDescription())));
    }

    public void dispatch(String message) throws CommandSyntaxException {
        dispatcher.execute(message, commandSource);
    }

    /**
     * Initialize a command with this method.
     * @param command is the command???? I mean, usual stuff, it extends {@link LegoCommand}.
     */
    private void registerCommand(Command command) {
        command.register(dispatcher);
        register(command);
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("prefix", prefix);
        return object;
    }

    @Override
    public void fromJson(JsonElement element) {
        setPrefix(element.getAsJsonObject().get("prefix").getAsString());
    }

    public List<Command> getCommands() {
        return items;
    }

}
