package net.staro.lego.events;

import lombok.Getter;
import lombok.Setter;

/**
 * An event that can be canceled by listeners.
 */
@Getter
@Setter
public class CancellableEvent {
    private boolean cancelled;

    public CancellableEvent() {
        cancelled = false;
    }

}
