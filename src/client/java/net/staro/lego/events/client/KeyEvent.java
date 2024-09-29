package net.staro.lego.events.client;

import lombok.Getter;
import lombok.Setter;
import net.staro.lego.events.CancellableEvent;

@Getter
@Setter
public class KeyEvent extends CancellableEvent {
    public static final KeyEvent INSTANCE = new KeyEvent();
    private int action;
    private int key;

}
