package com.mdjoon.multi_lang_search;

import com.mdjoon.multi_lang_search.config.ConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ReloadListener implements ResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(@NonNull ResourceManager manager) {
        ConfigManager.load();
        MultiLanguageCache.reload();

        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null) {
            player.connection.updateSearchTrees();
        }
    }
}
