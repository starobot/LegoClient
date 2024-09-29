package net.staro.lego.service.impl;

import net.staro.lego.Lego;
import net.staro.lego.api.event.bus.Priority;
import net.staro.lego.api.event.listener.Listener;
import net.staro.lego.events.client.ShutdownEvent;
import net.staro.lego.service.Service;

public class ShutdownService extends Service {
    private final Lego lego;

    public ShutdownService(Lego lego) {
        super(lego);
        this.lego = lego;
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGHEST)
    public void onShutdown(ShutdownEvent event) {
        lego.configManager().saveEverything(lego);
    }

}
