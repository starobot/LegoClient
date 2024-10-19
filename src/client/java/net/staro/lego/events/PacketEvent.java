package net.staro.lego.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;

/**
 * PacketEvent form meteor.
 * @author seasnail8169
 */
public class PacketEvent {
    public static class Receive extends CancellableEvent {
        private static final Receive INSTANCE = new Receive();

        public Packet<?> packet;
        public ClientConnection connection;

        public static Receive get(Packet<?> packet, ClientConnection connection) {
            INSTANCE.setCancelled(false);
            INSTANCE.packet = packet;
            INSTANCE.connection = connection;
            return INSTANCE;
        }
    }

    public static class Send extends CancellableEvent {
        private static final Send INSTANCE = new Send();

        public Packet<?> packet;
        public ClientConnection connection;

        public static Send get(Packet<?> packet, ClientConnection connection) {
            INSTANCE.setCancelled(false);
            INSTANCE.packet = packet;
            INSTANCE.connection = connection;
            return INSTANCE;
        }
    }

    public static class Sent {
        private static final Sent INSTANCE = new Sent();

        public Packet<?> packet;
        public ClientConnection connection;

        public static Sent get(Packet<?> packet, ClientConnection connection) {
            INSTANCE.packet = packet;
            INSTANCE.connection = connection;
            return INSTANCE;
        }
    }
}

