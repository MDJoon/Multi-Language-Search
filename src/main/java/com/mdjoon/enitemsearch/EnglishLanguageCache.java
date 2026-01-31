package com.mdjoon.enitemsearch;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.util.Language;

import java.util.List;

public class EnglishLanguageCache {
    private static TranslationStorage ENGLISH;

    public static TranslationStorage get() {
        return ENGLISH;
    }

    public static void reload() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        ENGLISH = TranslationStorage.load(
                client.getResourceManager(),
                List.of("en_us"),
                false
        );
    }
}
