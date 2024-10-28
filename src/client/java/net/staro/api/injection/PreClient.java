package net.staro.api.injection;

/**
 * This is for the part of the client which can be initialized before the MinecraftClient itself.
 * Not suitable for most managers and services.
 */
public interface PreClient
{
    /**
     * Initialize whatever object needed in {@link net.staro.lego.mixin.MixinMain}
     */
    void onInitialize();

}
