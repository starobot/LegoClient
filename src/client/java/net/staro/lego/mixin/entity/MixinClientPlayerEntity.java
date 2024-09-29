package net.staro.lego.mixin.entity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.staro.lego.Lego;
import net.staro.lego.events.tick.PostUpdateEvent;
import net.staro.lego.events.tick.PreUpdateEvent;
import net.staro.lego.events.tick.UpdateEvent;
import net.staro.lego.module.modules.Velocity;
import net.staro.lego.utility.MixinHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {
    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void pushOutOfBlocksSpaceHook(double d, double e, CallbackInfo ci) {
        MixinHelper.hook(new Velocity.PushOutOfBlocks(), ci);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.AFTER))
    private void tickMotionHook(CallbackInfo ci) {
        MixinHelper.hook(new UpdateEvent(ClientPlayerEntity.class.cast(this)), ci);
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;tickables:Ljava/util/List;", shift = At.Shift.BEFORE))
    private void postMotionPlayerUpdateHook(CallbackInfo ci) {
        Lego.EVENT_BUS.post(new PostUpdateEvent());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V", shift = At.Shift.AFTER))
    private void postSuperTickHook(CallbackInfo ci) {
        Lego.EVENT_BUS.post(new PreUpdateEvent());
    }

}

