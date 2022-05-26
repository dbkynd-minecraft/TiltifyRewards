package com.dbkynd.tiltifyrewards.command;

import com.dbkynd.tiltifyrewards.TiltifyRewards;
import com.dbkynd.tiltifyrewards.http.JsonBodyHandler;
import com.dbkynd.tiltifyrewards.http.UserCampaignsResponse;
import com.dbkynd.tiltifyrewards.http.TiltifyUser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

public class RegisterByName {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tiltify")
                .then(CommandManager.literal("register")
                    .then(CommandManager.literal("byName")
                            .then(CommandManager.argument("name", word())
                                    .executes(RegisterByName::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException{
        String name = getString(context, "name");
        TiltifyRewards.LOGGER.info("Registering tiltify campaign by username => " + name);
        context.getSource().sendFeedback(Text.literal("Finding campaign for user: " + name), false);

        UserCampaignsResponse campaign;

        try {
            var client = HttpClient.newHttpClient();
            var userRequest = HttpRequest.newBuilder(
                            URI.create("https://tiltify.com/api/v3/users/" + name))
                    .header("accept", "application/json")
                    .build();

            var userResponse = client.send(userRequest, new JsonBodyHandler<>(TiltifyUser.class));

            TiltifyUser user = userResponse.body().get();

            var campaignRequest = HttpRequest.newBuilder(
                            URI.create("https://tiltify.com/api/v3/users/" + user.id + "/campaigns"))
                    .header("accept", "application/json")
                    .build();

            var campaignResponse = client.send(campaignRequest, new JsonBodyHandler<>(UserCampaignsResponse.class));

            campaign = campaignResponse.body().get();
        } catch(Exception error) {
            context.getSource().sendFeedback(Text.literal("Tiltify API error"), false);
            error.printStackTrace();
            return 1;
        }

        TiltifyRewards.LOGGER.info("Registered tiltify campaign id:" + campaign.data.id + " - " + campaign.data.name);

        context.getSource().sendFeedback(Text.literal(campaign.data.username), false);
        context.getSource().sendFeedback(Text.literal(campaign.data.name), false);
        context.getSource().sendFeedback(Text.literal("Registered"), false);

        // TODO: store this to a config file and reconnect with new value
        return 1;
    }
}
