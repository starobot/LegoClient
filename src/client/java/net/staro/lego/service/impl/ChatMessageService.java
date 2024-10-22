package net.staro.lego.service.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.extern.slf4j.Slf4j;
import net.staro.api.Priority;
import net.staro.api.annotation.Listener;
import net.staro.lego.Lego;
import net.staro.lego.events.chat.ChatEvent;
import net.staro.lego.events.chat.InputSuggestorEvent;
import net.staro.lego.service.Service;

@Slf4j
public class ChatMessageService extends Service {
    private final Lego lego;

    public ChatMessageService(Lego lego) {
        super(lego);
        this.lego = lego;
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGHEST)
    public void onChatEvent(ChatEvent event) {
        var commandManager = lego.commandManager();
        var prefix = commandManager.getPrefix();
        if (event.getMessage().startsWith(prefix)) {
            try {
                commandManager.dispatch(event.getMessage().substring(prefix.length()));
            } catch (CommandSyntaxException e) {
                log.debug(e.toString());
            }

            event.setCancelled(true);
        }
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGHEST)
    public void onInputSuggestion(InputSuggestorEvent event) {
        var commandManager = lego.commandManager();
        var prefix = commandManager.getPrefix();
        int length = prefix.length();
        var reader = event.getReader();
        if (reader.canRead(length) && reader.getString().startsWith(prefix, reader.getCursor())) {
            reader.setCursor(reader.getCursor() + length);
            if (event.getParse() == null) {
                event.setParse(commandManager.getDispatcher().parse(reader,
                        commandManager.getCommandSource()));
            }

            int cursor = event.getTextField().getCursor();
            event.setPendingSuggestions(commandManager.getDispatcher().getCompletionSuggestions(event.getParse(), cursor));
            event.setCancelled(true);
        }
    }

}
