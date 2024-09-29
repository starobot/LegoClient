package net.staro.lego.mixin.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.staro.lego.module.modules.Velocity;
import net.staro.lego.utility.MixinHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@SuppressWarnings({"EqualsBetweenInconvertibleTypes", "DiscouragedShift"})
@Mixin(Entity.class)
public class MixinEntity {
    @Inject(method = "pushAwayFrom",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;sqrt(D)D", shift = At.Shift.BEFORE),
            cancellable = true)
    private void entityPushHook(Entity entity, CallbackInfo ci) {
        if (Objects.equals(this, MinecraftClient.getInstance().player) || Objects.equals(entity, MinecraftClient.getInstance().player)) {
            MixinHelper.hook(new Velocity.EntityPush(), ci);
        }
    }

}
