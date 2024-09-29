package net.staro.lego.manager.managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.staro.lego.Lego;
import net.staro.lego.api.Module;
import net.staro.lego.api.trait.Jsonable;
import net.staro.lego.manager.GenericManager;
import net.staro.lego.module.Category;
import net.staro.lego.module.modules.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ModuleManager extends GenericManager<Module> implements Jsonable {
    private final String configName = "modules.json";

    @Override
    public void initialize(Lego lego) {
        register(new Step(lego));
        register(new Notifications(lego));
        register(new Velocity(lego));
        register(new ClickGui(lego));
        register(new Fullbright(lego));
        register(new Heaven(lego));
    }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        getModules().forEach(module -> object.add(module.getName(), module.toJson()));
        return object;
    }

    @Override
    public void fromJson(JsonElement element) {
        getModules().forEach(module -> module.fromJson(element.getAsJsonObject().get(module.getName())));
    }

    public List<Module> getModules() {
        return items;
    }

    public ArrayList<Module> getEnabledModules() {
        return getModules().stream()
                .filter(Module::isEnabled)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Module getModuleByName(String name) {
        return getModules().stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }

    public ArrayList<Module> getModulesByCategory(Category category) {
        ArrayList<Module> modulesCategory = new ArrayList<>();
        getModules().forEach(module -> {
            if (module.getCategory() == category) {
                modulesCategory.add(module);
            }
        });
        return modulesCategory;
    }

}
