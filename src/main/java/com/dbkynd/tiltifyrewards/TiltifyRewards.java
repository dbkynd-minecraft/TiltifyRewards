package com.dbkynd.tiltifyrewards;

import com.dbkynd.tiltifyrewards.config.Config;
import com.dbkynd.tiltifyrewards.tiltify.WebSocketManager;
import com.moandjiezana.toml.Toml;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TiltifyRewards implements ModInitializer {
	public static final String MOD_ID = "tiltifyrewards";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Config CONFIG = new Config();

	@Override
	public void onInitialize() {
		ModRegistries.registerModStuffs();
		WebSocketManager.connect();
	}
}
