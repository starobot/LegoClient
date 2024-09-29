package net.staro.lego.api.event.bus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.Integer.MAX_VALUE;

/**
 * Represents the priority of an event listener.
 */
@Getter
@RequiredArgsConstructor
public enum Priority {
    HIGHEST(MAX_VALUE),
    HIGH(3),
    MEDIUM(2),
    LOW(1),
    DEFAULT(0);

    private final int val;

}
