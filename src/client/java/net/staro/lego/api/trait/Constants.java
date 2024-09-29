package net.staro.lego.api.trait;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.nio.file.Path;

public interface Constants {
    // ________ CLIENT NAME AND VERSION MAYBE????? ________ //
    String NAME = "Lego";
    String NAME_TO_LOWER_CASE = NAME.toLowerCase();

    // _______________ CONFIGS AND FOLDERS _______________ //
    File MINECRAFT_FOLDER = MinecraftClient.getInstance().runDirectory;
    File LEGO_FOLDER = new File(MINECRAFT_FOLDER, NAME_TO_LOWER_CASE);

    String MINECRAFT_FOLDER_PATH = MINECRAFT_FOLDER.getPath();

    Path LEGO_FOLDER_PATH = FabricLoader.getInstance().getGameDir().resolve(NAME_TO_LOWER_CASE);

}
