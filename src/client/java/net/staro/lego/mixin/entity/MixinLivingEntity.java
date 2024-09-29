package net.staro.lego.mixin.entity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.staro.lego.Lego;
import net.staro.lego.module.modules.Step;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @SuppressWarnings("ConstantValue")
    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void getStepHeightHook(CallbackInfoReturnable<Float> cir) {
        if (ClientPlayerEntity.class.isInstance(this)) {
            var event = new Step.StepHeightEvent(ClientPlayerEntity.class.cast(this), cir.getReturnValueF());
            Lego.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                cir.setReturnValue(event.getHeight());
            }
        }
    }

}
