package net.staro.lego.service;

import net.staro.lego.Lego;
import net.staro.lego.api.trait.Subscriber;

public abstract class Service implements Subscriber {
    protected Service(Lego lego) {
        subscribe(lego.eventBus());
    }

}
