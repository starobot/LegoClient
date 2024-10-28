package net.staro.api;

import lombok.Getter;

import static java.lang.Integer.MAX_VALUE;

/**
 * Represents the priority of an event listener.
 */
@Getter
public enum Priority
{
    HIGHEST(MAX_VALUE),
    HIGH(3),
    MEDIUM(2),
    LOW(1),
    DEFAULT(0);

    private final int val;

    Priority(int val)
    {
        this.val = val;
    }

}