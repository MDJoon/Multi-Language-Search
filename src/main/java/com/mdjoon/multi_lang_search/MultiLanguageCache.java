package com.mdjoon.multi_lang_search;

import com.mdjoon.multi_lang_search.config.ConfigManager;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;

public class MultiLanguageCache {
    private static ClientLanguage translationStorage;

    public static ClientLanguage get() {
        return translationStorage;
    }

    public static void reload() {
        Minecraft client = Minecraft.getInstance();

        String lang_code = ConfigManager.get().lang_code;
        translationStorage = ClientLanguage.loadFrom(
                client.getResourceManager(),
                List.of(lang_code),
                false
        );
    }
}
