package net.staro.lego.module.modules;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.staro.lego.Lego;
import net.staro.lego.api.Setting;
import net.staro.lego.api.event.listener.Listener;
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

    @Listener
    public void onPacketReceived(PacketEvent.Receive event) {
        if (event.packet instanceof EntityVelocityUpdateS2CPacket) {
            event.setCancelled(true);
        }

        if (event.packet instanceof ExplosionS2CPacket p) {
            var explodePacket = ((IExplosionS2CPacket) p);
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
