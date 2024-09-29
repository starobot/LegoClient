package net.staro.lego.mixin.client;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.staro.lego.events.client.KeyEvent;
import net.staro.lego.utility.MixinHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class MixinKeyboard {
    @Inject(method = "onKey", at = @At("TAIL"), cancellable = true)
    private void onKey(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen == null) {
            var event = KeyEvent.INSTANCE;
            event.setKey(key);
            event.setAction(action);
            MixinHelper.hook(event, ci);
        }
    }

}
