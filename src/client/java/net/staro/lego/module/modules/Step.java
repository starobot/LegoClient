package net.staro.lego.module.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.network.ClientPlayerEntity;
import net.staro.lego.Lego;
import net.staro.lego.api.Setting;
import net.staro.lego.api.event.listener.SafeListener;
import net.staro.lego.events.CancellableEvent;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

public class Step extends LegoModule {
    private final Setting<Float> height = floating("Height", 2, 0, 10);

    public Step(Lego lego) {
        super(lego, "Step", Category.MOVEMENT, "Steps up");
    }

    @SafeListener
    @SuppressWarnings("unused")
    public void onStepHeight(StepHeightEvent event) {
        event.setHeight(height.getValue());
    }

    @Getter
    @AllArgsConstructor
    public static class StepHeightEvent extends CancellableEvent {
        private final ClientPlayerEntity player;
        private float height;

        public void setHeight(float height) {
            setCancelled(true);
            this.height = height;
        }
    }

}
