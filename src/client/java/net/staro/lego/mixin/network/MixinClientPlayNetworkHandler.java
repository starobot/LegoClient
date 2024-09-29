package net.staro.lego.mixin.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.staro.lego.Lego;
import net.staro.lego.events.chat.ChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler {
    @Unique
    private boolean acceptMessage = true;

    @Shadow
    public abstract void sendChatMessage(String content);

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if (acceptMessage) {
            var event = new ChatEvent(message);
            Lego.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message);
                ci.cancel();
            } else {
                acceptMessage = false;
                sendChatMessage(event.getMessage());
                acceptMessage = true;
                ci.cancel();
            }
        }
    }

}
