package net.staro.lego.events.tick;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameloopEvent {
    public static final GameloopEvent INSTANCE = new GameloopEvent();
    private int ticks;

}
