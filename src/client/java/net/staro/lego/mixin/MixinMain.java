package net.staro.lego.mixin;

import net.minecraft.client.main.Main;
import net.staro.lego.PreClientInitializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public abstract class MixinMain {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Ljoptsimple/OptionParser;allowsUnrecognizedOptions()V"))
    private static void onInit(String[] args, CallbackInfo ci) {
        new PreClientInitializer().onInitialize();
    }

}
