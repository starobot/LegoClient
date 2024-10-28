package net.staro.lego.service;

import net.staro.api.trait.Subscriber;
import net.staro.lego.Lego;

public abstract class Service implements Subscriber {
    protected final Lego lego;

    protected Service(Lego lego) {
        this.lego = lego;
        subscribe(lego.eventBus());
    }

}
