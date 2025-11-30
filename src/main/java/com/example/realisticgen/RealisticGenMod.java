package com.example.realisticgen;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealisticGenMod implements ModInitializer {
	public static final String MOD_ID = "realisticgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Realistic Gen Mod!");

        // Register features, biomes, etc.
        RealisticWorldGen.init();
	}
}
