package net.staro.lego.events;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.packet.Packet;

public class PacketEvent {
    @Getter
    @Setter
    public static class Receive extends CancellableEvent {
        public static Receive INSTANCE = new Receive();
        private Packet<?> packet;
    }

    @Getter
    @Setter
    public static class Send extends CancellableEvent {
        public static Send INSTANCE = new Send();
        private Packet<?> packet;
    }

}

