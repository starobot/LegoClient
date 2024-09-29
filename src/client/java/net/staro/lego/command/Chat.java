package net.staro.lego.command;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.staro.lego.ducks.IChatHud;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;

// IMPORTANT: THESE ARE NOT NULL SAFE.
@RequiredArgsConstructor
public class Chat {
    private final MinecraftClient mc;

    /**
     * Sends a server-side message in chat.
     * @param message - the string to send in chat.
     * @param player - is a client-side player.
     */
    public void sendPlayerMessage(String message, ClientPlayerEntity player) {
        addToMessageHistory(message);
        player.networkHandler.sendChatMessage(message);
    }

    /**
     * Sends a server-side command in chat.
     * @param message - the string to send in chat.
     * @param player - is a client-side player.
     */
    public void sendCommand(String message, ClientPlayerEntity player) {
        addToMessageHistory(message);
        player.networkHandler.sendCommand(message);
    }

    /**
     * Sends a client-side message in chat.
     * @param message is a String to send.
     */
    public void send(String message) {
        send(Text.literal(message));
    }

    /**
     * Sends a client-side message in chat.
     * @param message is a Text to send.
     */
    public void send(Text message) {
        mc.inGameHud.getChatHud().addMessage(message);
    }

    public void send(Text message, String identifier) {
        send(mc.inGameHud.getChatHud(), message, identifier);
    }

    /**
     * Sends a client-side message in chat without leaving it in the log file.
     * @param message is a Text to send.
     */
    public void sendLogless(Text message) {
        sendLogless((IChatHud) mc.inGameHud.getChatHud(), message, null);
    }

    /**
     * Sends a client-side message in chat without leaving it in the log file.
     * @param message is a Text to send.
     * @param identifier is a String to mark the message. Useful in notifications
     *                  so only the messages with the same module
     *                   get deleted before sending another one.
     */
    public void sendLogless(Text message, String identifier) {
        sendLogless((IChatHud) mc.inGameHud.getChatHud(), message, identifier);
    }

    /**
     * Deletes an identifier of the message.
     * @param identifier is a String to mark the message. Useful in notifications
     *      *                  so only the messages with the same module
     *      *                   get deleted before sending another one.
     */
    public void delete(String identifier) {
        delete(new MessageSignatureData(get256Bytes(identifier)));
    }

    private void send(ChatHud chatHud, Text message, @Nullable String identifier) {
        var signature = identifier != null ? new MessageSignatureData(get256Bytes(identifier)) : null;
        delete(signature);
        chatHud.addMessage(message, signature, MessageIndicator.system());
    }

    private void sendLogless(IChatHud chatHud, Text message, @Nullable String identifier) {
        var signature = identifier != null ? new MessageSignatureData(get256Bytes(identifier)) : null;
        delete(signature);
        var chatHudLine = new ChatHudLine(
                mc.inGameHud.getTicks(),
                message,
                signature,
                MessageIndicator.system());
        chatHud.lego$addVisibleMessage(chatHudLine);
        chatHud.lego$addMessage(chatHudLine);
    }

    private void delete(@Nullable MessageSignatureData signature) {
        ((IChatHud) mc.inGameHud.getChatHud()).lego$deleteMessage(signature, true);
    }

    private void addToMessageHistory(String message) {
        mc.inGameHud.getChatHud().addToMessageHistory(message);
    }

    private byte[] get256Bytes(String identifier) {
        byte[] bytes = new byte[256];
        byte[] identifierBytes = identifier.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(identifierBytes, 0, bytes, 0, Math.min(bytes.length, identifierBytes.length));
        return bytes;
    }

}
