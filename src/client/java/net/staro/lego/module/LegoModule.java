package net.staro.lego.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.staro.api.Setting;
import net.staro.lego.Lego;
import net.staro.lego.events.client.ToggleEvent;
import net.staro.lego.setting.GenericSetting;
import net.staro.lego.setting.type.Bind;
import net.staro.lego.utility.SettingUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class LegoModule extends AbstractModule {
    private final Setting<Boolean> enabled = bool("Enabled", false, "Enables or disables the module.");
    private final Setting<Boolean> drawn = bool("Drawn", true, "Sets up the visibility of the module.");
    private final Setting<Bind> bind = bind("Bind", Bind.none(), "Binds the module to a specified key.");
    private final AtomicBoolean atomicEnabled = new AtomicBoolean();
    private final AtomicBoolean atomicDrawn = new AtomicBoolean();
    protected final MinecraftClient mc;

    public LegoModule(Lego lego, String name, Category category, String description) {
        super(lego, name, category, description);
        this.mc = lego.mc();
    }

    @Override
    public boolean isEnabled() {
        atomicEnabled.set(enabled.getValue());
        return atomicEnabled.get();
    }

    @Override
    public void enable() {
        if (!this.isEnabled()) {
            enabled.setValue(true);
            onEnable();
            lego.eventBus().subscribe(this);
        }
    }

    @Override
    public void disable() {
        if (this.isEnabled()) {
            enabled.setValue(false);
            onDisable();
            lego.eventBus().unsubscribe(this);
        }
    }

    @Override
    public void toggle() {
        lego.eventBus().post(new ToggleEvent(this, !this.isEnabled()));
        if (isEnabled()) {
            disable();
        } else {
            enable();
        }

    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @Override
    public List<GenericSetting<?>> getSettings() {
        return Stream.of(getClass(), getClass().getSuperclass(), getClass().getSuperclass().getSuperclass())
                .flatMap(c -> Arrays.stream(c.getDeclaredFields()))
                .filter(field -> field.getType().isAssignableFrom(GenericSetting.class))
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return (GenericSetting<?>) field.get(this);
                    }
                    catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .peek(s -> s.setModule(this))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public GenericSetting<?> getSettingByName(String name) {
        return getSettings().stream().filter(setting -> setting.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        getSettings().forEach(setting -> {
                    try {
                        if (setting.getValue() instanceof Bind) {
                            object.addProperty(setting.getName(), ((Bind) setting.getValue()).getKey());
                        } else {
                            object.addProperty(setting.getName(), setting.getValue().toString());
                        }
                    } catch (Throwable e) {
                        log.debug(e.getMessage());
                    }
                });
        return object;
    }

    @Override
    @SneakyThrows
    public void fromJson(JsonElement element) {
        if (element != null) {
            JsonObject object = element.getAsJsonObject();
            String enabled = object.get("Enabled").getAsString();
            if (Boolean.parseBoolean(enabled)) toggle();
            getSettings().forEach(setting -> {
                try {
                    SettingUtil.setValueFromJson(this, setting, object.get(setting.getName()));
                } catch (Throwable throwable) {
                    log.debug(throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void setBind(int key) {
        this.bind.setValue(new Bind(key));
    }

    @Override
    public Bind getBind() {
        return this.bind.getValue();
    }

    @Override
    public boolean isDrawn() {
        atomicDrawn.set(drawn.getValue());
        return atomicDrawn.get();
    }

    @Override
    public void drawn(boolean state) {
        drawn.setValue(state);
    }

}
