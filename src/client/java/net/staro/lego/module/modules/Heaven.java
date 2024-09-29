package net.staro.lego.module.modules;

import net.staro.lego.Lego;
import net.staro.lego.api.event.listener.SafeListener;
import net.staro.lego.events.camera.CameraUpdateEvent;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

public class Heaven extends LegoModule {
    public Heaven(Lego lego) {
        super(lego, "Heaven", Category.RENDER, "Shows you what heaven is like.");
    }

    @SuppressWarnings("unused")
    @SafeListener
    public void onCameraUpdate(CameraUpdateEvent event) {
        mc.worldRenderer.reload();
    }

}
