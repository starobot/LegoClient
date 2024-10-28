package net.staro.lego.service.impl;

import net.staro.api.Priority;
import net.staro.api.annotation.Listener;
import net.staro.lego.Lego;
import net.staro.lego.events.client.ShutdownEvent;
import net.staro.lego.service.Service;

public class ShutdownService extends Service {
    public ShutdownService(Lego lego) {
        super(lego);
    }

    @SuppressWarnings("unused")
    @Listener(priority = Priority.HIGHEST)
    public void onShutdown(ShutdownEvent event) {
        lego.configManager().saveEverything(lego);
    }

}
