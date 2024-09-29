package net.staro.lego.mixin.render;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.staro.lego.events.camera.CameraUpdateEvent;
import net.staro.lego.utility.MixinHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void onUpdate(BlockView area,
                          Entity focusedEntity,
                          boolean thirdPerson,
                          boolean inverseView,
                          float tickDelta,
                          CallbackInfo ci) {
        var event = CameraUpdateEvent.INSTANCE;
        event.setArea(area);
        event.setFocusedEntity(focusedEntity);
        event.setThirdPerson(thirdPerson);
        event.setInverseView(inverseView);
        event.setTickDelta(tickDelta);
        MixinHelper.hook(event, ci);
    }

}
