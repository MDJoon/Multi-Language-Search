package com.mdjoon.enitemsearch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Enitemsearch implements ModInitializer {
	public static final String MOD_ID = "enitemsearch";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(Identifier.of(MOD_ID), new ReloadListener());
	}
}