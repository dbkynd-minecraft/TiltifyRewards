package com.dbkynd.tiltifyrewards.command;

import com.dbkynd.tiltifyrewards.TiltifyRewards;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class RegisterTiltifyID {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tiltify")
                .then(CommandManager.literal("register")
                        .then(CommandManager.argument("campaign_id", integer())
                                .executes(RegisterTiltifyID::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        TiltifyRewards.LOGGER.info("COMMAND RAN");
        int id = getInteger(context, "campaign_id");
        context.getSource().sendFeedback(Text.literal(String.valueOf(id)), false);
        // TODO: store this to a config file and disconnect reconnect with new value
        // TODO: see what info we can get from the api and show message in chat about name of event
        return 1;
    }
}
