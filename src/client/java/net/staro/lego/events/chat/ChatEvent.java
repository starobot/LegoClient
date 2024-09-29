package net.staro.lego.events.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.staro.lego.events.CancellableEvent;

@Getter
@RequiredArgsConstructor
public class ChatEvent extends CancellableEvent {
    private final String message;

}
