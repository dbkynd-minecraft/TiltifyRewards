package com.dbkynd.tiltifyrewards;

import com.dbkynd.tiltifyrewards.command.RegisterTiltifyID;
import com.dbkynd.tiltifyrewards.command.TiltifyWsStatus;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(RegisterTiltifyID::register);
        CommandRegistrationCallback.EVENT.register(TiltifyWsStatus::register);
    }
}
