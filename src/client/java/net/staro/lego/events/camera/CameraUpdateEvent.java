package net.staro.lego.events.camera;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.staro.lego.events.CancellableEvent;

@Getter
@Setter
public class CameraUpdateEvent extends CancellableEvent {
    public static final CameraUpdateEvent INSTANCE = new CameraUpdateEvent();

    private BlockView area;
    private Entity focusedEntity;
    private boolean thirdPerson;
    private boolean inverseView;
    private float tickDelta;

}
