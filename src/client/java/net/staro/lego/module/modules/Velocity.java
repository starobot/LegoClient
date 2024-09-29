package net.staro.lego.module.modules;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.staro.lego.Lego;
import net.staro.lego.api.Setting;
import net.staro.lego.api.event.listener.Listener;
import net.staro.lego.api.event.listener.SafeListener;
import net.staro.lego.events.CancellableEvent;
import net.staro.lego.events.PacketEvent;
import net.staro.lego.mixin.network.IExplosionS2CPacket;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

@SuppressWarnings("unused")
public class Velocity extends LegoModule {
    private final Setting<Boolean> pushOutOfBlocks = bool("PushOutOfBlocks", true, "No push from blocks.");
    private final Setting<Boolean> entityPush = bool("EntityPush", true, "No push from entities.");

    public Velocity(Lego lego) {
        super(lego, "Velocity", Category.MOVEMENT, "Manipulates the velocity");
    }

    @SuppressWarnings({"DataFlowIssue"})
    @SafeListener
    public void onEntityVelocityUpdateS2CPacket(PacketEvent.Receive event) {
        var player = mc.player;
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            if (((EntityVelocityUpdateS2CPacket) event.getPacket()).getEntityId() == player.getId()) {
                event.setCancelled(true);
            }
        }
    }

    @Listener
    public void onPacketReceived(PacketEvent.Receive event) {
        if (event.getPacket() instanceof ExplosionS2CPacket) {
            var explodePacket = ((IExplosionS2CPacket) event.getPacket());
            explodePacket.setMotionX(0);
            explodePacket.setMotionY(0);
            explodePacket.setMotionZ(0);
        }
    }

    @Listener
    public void onPushOutOfBlocks(PushOutOfBlocks event) {
        event.setCancelled(pushOutOfBlocks.getValue());
    }

    @Listener
    public void onEntityPush(EntityPush event) {
        event.setCancelled(entityPush.getValue());
    }

    public static final class PushOutOfBlocks extends CancellableEvent {}

    public static final class EntityPush extends CancellableEvent {}

}
