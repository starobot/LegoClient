package net.staro.lego.events.tick;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.network.ClientPlayerEntity;
import net.staro.lego.events.CancellableEvent;

@Getter
@RequiredArgsConstructor
public class UpdateEvent extends CancellableEvent {
    private final ClientPlayerEntity player;

}
