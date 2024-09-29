package net.staro.lego.service.impl;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.api.Module;
import net.staro.lego.api.event.bus.Priority;
import net.staro.lego.api.event.listener.Listener;
import net.staro.lego.events.client.KeyEvent;
import net.staro.lego.service.Service;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicBoolean;

public class KeyboardService extends Service {
    private final Lego lego;
    @Getter
    private final AtomicBoolean listening = new AtomicBoolean();
    @Setter
    private Module module;

    public KeyboardService(Lego lego) {
        super(lego);
        this.lego = lego;
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGH)
    public void onKeyPressed(KeyEvent event) {
        if (event.getAction() == 1 && !listening.get()) {
            lego.moduleManager().getModules().stream()
                    .filter(module -> module.getBind().getKey() == event.getKey())
                    .forEach(Module::toggle);
        }
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGHEST)
    public void onBindSet(KeyEvent event) {
        if (listening.get()) {
            listening.set(false);
            if (event.getKey() == GLFW.GLFW_KEY_ESCAPE) {
                lego.chat().sendLogless(Text.literal("Operation cancelled").formatted(Formatting.GRAY));
                return;
            }

            module.setBind(event.getKey());
            lego.chat().sendLogless(Text.literal(
                    "The bind for " + module.getName().formatted(Formatting.WHITE).formatted(Formatting.BOLD)
                            + " has been set to " + module.getBind().getBindName()).formatted(Formatting.GRAY));
        }
    }

}
