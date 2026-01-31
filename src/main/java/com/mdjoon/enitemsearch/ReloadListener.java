package com.mdjoon.enitemsearch;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ReloadListener implements SynchronousResourceReloader {
    @Override
    public void reload(ResourceManager manager) {
        EnglishLanguageCache.reload();
    }
}
