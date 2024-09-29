package net.staro.lego.mixin.render;

import net.minecraft.client.render.LightmapTextureManager;
import net.staro.lego.Lego;
import net.staro.lego.module.modules.Fullbright;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LightmapTextureManager.class)
public abstract class MixinLightmapTextureManager {
    @ModifyArg(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"),
            index = 2)
    private int colorHook(int color) {
        var event = Fullbright.LightmapTexture.INSTANCE;
        event.setColor(color);
        Lego.EVENT_BUS.post(event);
        return event.getColor();
    }

}
