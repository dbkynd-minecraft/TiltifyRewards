package com.dbkynd.tiltifyrewards;

import com.dbkynd.tiltifyrewards.tiltify.WebSocket;
import com.dbkynd.tiltifyrewards.tiltify.WebSocketManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class TiltifyRewards implements ModInitializer {
	public static final String MOD_ID = "tiltifyrewards";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModRegistries.registerModStuffs();
		WebSocketManager.connect();
	}
}
