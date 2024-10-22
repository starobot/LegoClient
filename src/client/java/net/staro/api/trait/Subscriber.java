package net.staro.api.trait;

import net.staro.api.EventBus;

// rofl
public interface Subscriber {
    default void subscribe(EventBus eventBus) {
        eventBus.subscribe(this);
    }

}
