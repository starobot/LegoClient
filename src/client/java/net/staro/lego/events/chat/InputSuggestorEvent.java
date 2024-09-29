package net.staro.lego.events.chat;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.staro.lego.events.CancellableEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@AllArgsConstructor
public class InputSuggestorEvent extends CancellableEvent {
    private final TextFieldWidget textField;
    private final StringReader reader;
    private @Nullable ParseResults<CommandSource> parse;
    private @Nullable CompletableFuture<Suggestions> pendingSuggestions;
    private @Nullable ChatInputSuggestor.SuggestionWindow window;
    private boolean completingSuggestions;

}
