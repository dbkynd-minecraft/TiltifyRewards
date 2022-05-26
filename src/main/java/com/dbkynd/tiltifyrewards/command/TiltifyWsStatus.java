package com.dbkynd.tiltifyrewards.command;

import com.dbkynd.tiltifyrewards.tiltify.WebSocket;
import com.dbkynd.tiltifyrewards.tiltify.WebSocketManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TiltifyWsStatus {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tiltify")
                .then(CommandManager.literal("status").executes(TiltifyWsStatus::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        WebSocket ws = WebSocketManager.getWs();
        if (ws.isOpen()) {
            context.getSource().sendFeedback(Text.literal("Tiltify connection is OPEN"), false);
        } else {
            context.getSource().sendFeedback(Text.literal("Tiltify connection is CLOSED"), false);
        }
        return 1;
    }
}
