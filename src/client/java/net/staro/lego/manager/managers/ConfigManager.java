package net.staro.lego.manager.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.staro.api.trait.Constants;
import net.staro.api.trait.Jsonable;
import net.staro.lego.Lego;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("LoggingSimilarMessage")
@Slf4j
@Getter
public class ConfigManager {
    private final Gson gson;
    private boolean firstLaunch;
    private String currentConfigName = "modules";
    private final Path currentConfigPath = Constants.LEGO_FOLDER_PATH.resolve("current_config.json");
    private final Path moduleConfigsPath = Constants.LEGO_FOLDER_PATH.resolve("modules");
    private final List<String> moduleConfigs = new ArrayList<>();

    public ConfigManager() {
        var folder = Constants.LEGO_FOLDER;
        if (folderDoesNotExist(folder)) {
            firstLaunch = true;
            if (!folder.mkdirs()) {
                throw new IllegalStateException("Failed to create directory: " + folder.getAbsolutePath());
            }
        }

        if (folderDoesNotExist(moduleConfigsPath.toFile())) {
            if (!moduleConfigsPath.toFile().mkdirs()) {
                throw new IllegalStateException("Failed to create directory: " + folder.getAbsolutePath());
            }
        }

        this.gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
        loadCurrentConfigName();
        loadModuleConfigNames();
    }

    public void saveEverything(Lego lego) {
        lego.jsonableManager()
                .getItems()
                .forEach(jsonable -> {
                    try {
                        Path configPath = getConfigPath(jsonable);
                        Files.writeString(configPath, gson.toJson(jsonable.toJson()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void loadEverything(Lego lego) {
        if (!currentConfigName.equals("default")) {
            loadConfig(currentConfigName, lego);
        } else {
            lego.jsonableManager()
                    .getItems()
                    .forEach(jsonable -> {
                        try {
                            String read = Files.readString(Constants.LEGO_FOLDER_PATH.resolve(jsonable.getConfigName()));
                            jsonable.fromJson(JsonParser.parseString(read));
                        } catch (Throwable e) {
                            log.debug(e.getMessage());
                        }
                    });
        }
    }

    @SneakyThrows
    public void saveConfig(String name, Lego lego) {
        Path configPath = moduleConfigsPath.resolve(name + ".json");
        Files.writeString(configPath, gson.toJson(lego.moduleManager().toJson()));
        currentConfigName = name;
        saveCurrentConfigName();
        if (!moduleConfigs.contains(name)) {
            moduleConfigs.add(name);
        }
    }

    @SneakyThrows
    public void loadConfig(String name, Lego lego) {
        Path configPath = moduleConfigsPath.resolve(name + ".json");
        if (Files.exists(configPath)) {
            String read = Files.readString(configPath);
            lego.moduleManager().fromJson(JsonParser.parseString(read));
            currentConfigName = name;
            saveCurrentConfigName();
        } else {
            log.warn("Config '{}' not found.", name);
        }
    }

    public void deleteConfig(String name) {
        Path configPath = moduleConfigsPath.resolve(name + ".json");
        if (Files.exists(configPath)) {
            try {
                Files.delete(configPath);
                moduleConfigs.remove(name);
                log.info("Config '{}' deleted successfully.", name);
                if (currentConfigName.equals(name)) {
                    currentConfigName = "default";
                    saveCurrentConfigName();
                }
            } catch (IOException e) {
                log.error("Failed to delete config '{}'.", name, e);
            }
        } else {
            log.warn("Config '{}' not found.", name);
        }
    }

    private void loadModuleConfigNames() {
        File[] files = moduleConfigsPath.toFile().listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                String configName = file.getName().replace(".json", "");
                moduleConfigs.add(configName);
            }
        }
    }

    private Path getConfigPath(Jsonable jsonable) {
        if (jsonable instanceof ModuleManager) {
            if (!currentConfigName.equals("default")) {
                return moduleConfigsPath.resolve(currentConfigName + ".json");
            } else {
                return moduleConfigsPath.resolve("modules.json");
            }
        } else {
            return Constants.LEGO_FOLDER_PATH.resolve(jsonable.getConfigName());
        }
    }

    private void loadCurrentConfigName() {
        try {
            if (Files.exists(currentConfigPath)) {
                String json = Files.readString(currentConfigPath);
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                currentConfigName = jsonObject.get("currentConfig").getAsString();
            }
        } catch (IOException e) {
            log.error("Failed to load current config name.", e);
        }
    }

    private void saveCurrentConfigName() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("currentConfig", currentConfigName);
            Files.writeString(currentConfigPath, gson.toJson(jsonObject));
        } catch (IOException e) {
            log.error("Failed to save current config name.", e);
        }
    }


    private boolean folderDoesNotExist(File folder) {
        return !folder.exists();
    }

}
