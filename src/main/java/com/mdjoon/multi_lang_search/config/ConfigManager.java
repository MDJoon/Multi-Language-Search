package com.mdjoon.multi_lang_search.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mdjoon.multi_lang_search.MultiLangSearch;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static net.fabricmc.fabric.impl.resource.pack.ModPackResourcesUtil.GSON;

public class ConfigManager {
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("lang.json");

    private static ModConfig config;

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                config = GSON.fromJson(reader, ModConfig.class);
            } catch ( IOException e) {
                MultiLangSearch.LOGGER.error(e.getMessage(), e);
            }
        } else {
            config = new ModConfig();
            save();
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            MultiLangSearch.LOGGER.error(e.getMessage(), e);
        }
    }

    public static ModConfig get() {
        if(config == null) load();
        return config;
    }
}
