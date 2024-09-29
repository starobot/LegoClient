package net.staro.lego.module.modules;

import lombok.Getter;
import lombok.Setter;
import net.staro.lego.Lego;
import net.staro.lego.api.event.listener.Listener;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

public class Fullbright extends LegoModule {
    public Fullbright(Lego lego) {
        super(lego, "Fullbright", Category.RENDER, "Makes the world bright.");
    }

    @Listener
    @SuppressWarnings("unused")
    public void onLightmapTexture(LightmapTexture event) {
        event.setColor(0xFFFFFFFF);
    }

    @Getter
    @Setter
    public static class LightmapTexture {
        public static final LightmapTexture INSTANCE = new LightmapTexture();
        private int color;
    }

}
