package com.dbkynd.tiltifyrewards;

import com.dbkynd.tiltifyrewards.command.RegisterByName;
import com.dbkynd.tiltifyrewards.command.RegisterById;
import com.dbkynd.tiltifyrewards.command.TiltifyWsStatus;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(RegisterByName::register);
        CommandRegistrationCallback.EVENT.register(RegisterById::register);
        CommandRegistrationCallback.EVENT.register(TiltifyWsStatus::register);
    }
}
