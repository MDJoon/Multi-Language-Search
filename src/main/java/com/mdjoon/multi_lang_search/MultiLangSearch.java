package com.mdjoon.multi_lang_search;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiLangSearch implements ClientModInitializer {
	public static final String MOD_ID = "multi-lang_search";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(Identifier.parse(MOD_ID), new ReloadListener());
	}
}