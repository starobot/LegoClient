package net.staro.lego.events.client;

import net.staro.api.Module;

public record ToggleEvent(Module module, boolean state) {
}
