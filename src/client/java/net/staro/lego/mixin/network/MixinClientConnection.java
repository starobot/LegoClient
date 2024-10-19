package net.staro.lego.mixin.network;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.staro.lego.Lego;
import net.staro.lego.events.PacketEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@SuppressWarnings("UnreachableCode")
@Mixin(ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onHandlePacket(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof BundleS2CPacket bundle) {
            for (Iterator<Packet<? super ClientPlayPacketListener>> it = bundle.getPackets().iterator(); it.hasNext(); ) {
                var event = PacketEvent.Receive.get(it.next(), (ClientConnection) (Object) this);
                Lego.EVENT_BUS.post(event);
                if (event.isCancelled()) {
                    it.remove();
                }
            }
        } else {
            var event = PacketEvent.Receive.get(packet, (ClientConnection) (Object) this);
            Lego.EVENT_BUS.post(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", cancellable = true)
    private void onSendPacketHead(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) {
        var event = (PacketEvent.Send.get(packet, (ClientConnection) (Object) this));
        Lego.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("TAIL"))
    private void onSendPacketTail(Packet<?> packet, @Nullable PacketCallbacks callbacks, CallbackInfo ci) {
        var event = PacketEvent.Sent.get(packet, (ClientConnection) (Object) this);
        Lego.EVENT_BUS.post(event);
    }


}
