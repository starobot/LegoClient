package net.staro.lego.api.event.listener;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;

import java.lang.reflect.Method;

/**
 * An extent of Listener to assert {@code mc.player} and {@code mc.world} != null
 */
public class SafeEventListener extends EventListener {
    private final MinecraftClient mc;

    public SafeEventListener(Object instance, Method method, int priority) {
        super(instance, method, priority);
        this.mc = MinecraftClient.getInstance();
    }

    @Override
    public void invoke(Object event) {
        safe(mc, (player, world, interactionManager) -> super.invoke(event));
    }

    private void safe(MinecraftClient mc, PlayerAndWorldConsumer action) {
        safeOr(mc, action, () -> {});
    }

    private void safeOr(MinecraftClient mc, PlayerAndWorldConsumer action, Runnable or) {
        ClientWorld world = mc.world;
        ClientPlayerEntity player = mc.player;
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;
        if (world != null && player != null && interactionManager != null) {
            action.accept(player, world, interactionManager);
        } else {
            or.run();
        }
    }

    @FunctionalInterface
    public interface PlayerAndWorldConsumer {
        /**
         *
         * @param player {@link MinecraftClient#player}
         * @param world {@link MinecraftClient#world}
         * @param interactionManager {@link MinecraftClient#interactionManager}
         */
        void accept(ClientPlayerEntity player, ClientWorld world, ClientPlayerInteractionManager interactionManager);
    }

}
