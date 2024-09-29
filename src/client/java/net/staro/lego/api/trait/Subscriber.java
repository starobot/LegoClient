package net.staro.lego.api.trait;

import net.staro.lego.api.event.bus.EventBus;

// rofl
public interface Subscriber {
    default void subscribe(EventBus eventBus) {
        eventBus.subscribe(this);
    }

}
