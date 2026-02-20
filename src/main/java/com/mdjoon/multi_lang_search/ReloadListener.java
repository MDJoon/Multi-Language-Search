package com.mdjoon.multi_lang_search;

import com.mdjoon.multi_lang_search.config.ConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

@Environment(EnvType.CLIENT)
public class ReloadListener implements SynchronousResourceReloader {
    @Override
    public void reload(ResourceManager manager) {
        MultiLanguageCache.reload();
        ConfigManager.load();
    }
}
