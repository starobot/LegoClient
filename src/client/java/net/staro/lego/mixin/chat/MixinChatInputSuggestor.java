package net.staro.lego.mixin.chat;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.staro.lego.Lego;
import net.staro.lego.events.chat.InputSuggestorEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class MixinChatInputSuggestor {
    @Shadow
    private ParseResults<CommandSource> parse;
    @Shadow
    @Final
    TextFieldWidget textField;
    @Shadow
    boolean completingSuggestions;
    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow
    private ChatInputSuggestor.SuggestionWindow window;
    @Shadow
    protected abstract void showCommandSuggestions();

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "refresh",
            at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false),
            cancellable = true)
    public void onRefresh(CallbackInfo ci, @Local StringReader reader) {
        var event = new InputSuggestorEvent(textField, reader, parse, pendingSuggestions, window, completingSuggestions);
        Lego.EVENT_BUS.post(event);
        parse = event.getParse();
        if (event.isCancelled()) {
            int cursor = event.getTextField().getCursor();
            if (cursor >= 1 && (window == null || !completingSuggestions)) {
                pendingSuggestions = event.getPendingSuggestions();
                // if this ever crashes - then ill add a nullcheck.
                pendingSuggestions.thenRun(() -> {
                    if (pendingSuggestions.isDone()) {
                        showCommandSuggestions();
                    }
                });
            }

            ci.cancel();
        }
    }

}
