package net.staro.lego.ducks;

import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.network.message.MessageSignatureData;
import org.jetbrains.annotations.Nullable;

public interface IChatHud {
    void lego$addMessage(@Nullable ChatHudLine message);

    void lego$addVisibleMessage(@Nullable ChatHudLine message);

    void lego$deleteMessage(@Nullable MessageSignatureData signature, boolean all);

}
