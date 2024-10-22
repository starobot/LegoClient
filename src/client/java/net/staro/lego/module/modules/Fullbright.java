package net.staro.lego.module.modules;

import lombok.Getter;
import lombok.Setter;
import net.staro.api.annotation.Listener;
import net.staro.lego.Lego;
import net.staro.lego.module.Category;
import net.staro.lego.module.LegoModule;

public class Fullbright extends LegoModule {
    public Fullbright(Lego lego) {
        super(lego, "Fullbright", Category.RENDER, "Makes the world bright.");
    }

    @SuppressWarnings("unused")
    @Listener
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
