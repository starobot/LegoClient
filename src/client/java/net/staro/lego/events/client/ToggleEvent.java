package net.staro.lego.events.client;

import net.staro.lego.api.Module;

public record ToggleEvent(Module module, boolean state) {
}
