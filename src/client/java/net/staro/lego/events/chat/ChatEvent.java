package net.staro.lego.events.chat;

import lombok.Getter;
import lombok.Setter;
import net.staro.lego.events.CancellableEvent;

@Getter
@Setter
public class ChatEvent extends CancellableEvent {
    private String message;

}
