package net.staro.lego.service;

import net.staro.api.trait.Subscriber;
import net.staro.lego.Lego;

public abstract class Service implements Subscriber {
    protected Service(Lego lego) {
        subscribe(lego.eventBus());
    }

}
