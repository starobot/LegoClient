package net.staro.lego.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.staro.lego.Lego;
import net.staro.lego.api.Module;
import net.staro.lego.setting.SettingFactoryImpl;

@Getter
@RequiredArgsConstructor
public abstract class AbstractModule extends SettingFactoryImpl implements Module {
    protected final Lego lego;
    private final String name;
    private final Category category;
    private final String description;

    protected abstract void onEnable(Lego lego);

    protected abstract void onEnable();

    protected abstract void onDisable(Lego lego);

    protected abstract void onDisable();

}
