package net.staro.lego.service.impl;

import net.staro.lego.Lego;
import net.staro.lego.api.event.bus.Priority;
import net.staro.lego.api.event.listener.Listener;
import net.staro.lego.events.client.InitEvent;
import net.staro.lego.service.Service;

import java.io.IOException;

public class FirstLaunchService extends Service {
    private final String URL = "https://youtu.be/f9iE8b8jh2A?si=EjFttPahMeqfe9Z2";

    public FirstLaunchService(Lego lego) {
        super(lego);
    }

    @SuppressWarnings({"unused", "deprecation"})
    @Listener(priority = Priority.HIGHEST)
    public void onInitialize(InitEvent event) {
        if (event.lego().configManager().isFirstLaunch()) {
            new Thread(() -> {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + URL);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            event.lego().eventBus().unsubscribe(this);
        }
    }

}
