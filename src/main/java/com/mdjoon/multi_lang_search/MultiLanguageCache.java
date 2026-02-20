package com.mdjoon.multi_lang_search;

import com.mdjoon.multi_lang_search.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.TranslationStorage;

import java.util.List;

public class MultiLanguageCache {
    private static TranslationStorage translationStorage;

    public static TranslationStorage get() {
        return translationStorage;
    }

    public static void reload() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        String lang_code = ConfigManager.get().lang_code;
        translationStorage = TranslationStorage.load(
                client.getResourceManager(),
                List.of(lang_code),
                false
        );
    }
}
