package net.staro.lego.module.modules;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.api.event.listener.SafeListener;
import net.staro.lego.events.client.ToggleEvent;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

public class Notifications extends LegoModule {
    public Notifications(Lego lego) {
        super(lego, "Notifications", Category.CLIENT, "Shows selected notifications in chat.");
    }

    @SafeListener
    @SuppressWarnings("unused")
    public void onSettingChanged(ToggleEvent event) {
        var module = event.module();
        String message = event.state() ? " enabled." : " disabled.";
        Formatting formatting = event.state() ? Formatting.AQUA : Formatting.RED;
        lego.chat().sendLogless(Text.literal("")
                .append(Text.literal(module.getName()).formatted(Formatting.BOLD))
                .append(Text.literal(message).formatted(formatting)), module.getName());
    }

}
