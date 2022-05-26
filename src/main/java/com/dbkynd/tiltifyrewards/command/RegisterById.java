package com.dbkynd.tiltifyrewards.command;

import com.dbkynd.tiltifyrewards.TiltifyRewards;
import com.dbkynd.tiltifyrewards.http.CampaignResponse;
import com.dbkynd.tiltifyrewards.http.JsonBodyHandler;
import com.dbkynd.tiltifyrewards.http.UserCampaignsResponse;
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

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class RegisterById {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("tiltify")
                .then(CommandManager.literal("register")
                    .then(CommandManager.literal("byId")
                            .then(CommandManager.argument("campaign_id", integer())
                                    .executes(RegisterById::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int id = getInteger(context, "campaign_id");
        TiltifyRewards.LOGGER.info("Registering tiltify campaign by username => " + id);
        context.getSource().sendFeedback(Text.literal("Finding campaign id: " + id), false);

        CampaignResponse campaign;

        try {
            var client = HttpClient.newHttpClient();

            var campaignRequest = HttpRequest.newBuilder(
                            URI.create("https://tiltify.com/api/v3/campaigns/" + id))
                    .header("accept", "application/json")
                    .header("authorization", "Bearer xxxx") // TODO: hide token
                    .build();

            var campaignResponse = client.send(campaignRequest, new JsonBodyHandler<>(CampaignResponse.class));

            campaign = campaignResponse.body().get();
        } catch(Exception error) {
            context.getSource().sendFeedback(Text.literal("Tiltify API error."), false);
            error.printStackTrace();
            return 1;
        }

        TiltifyRewards.LOGGER.info("Registered tiltify campaign id:" + campaign.data.id + " - " + campaign.data.name);

        context.getSource().sendFeedback(Text.literal(campaign.data.username), false);
        context.getSource().sendFeedback(Text.literal(campaign.data.name), false);
        context.getSource().sendFeedback(Text.literal("Registered"), false);

        TiltifyRewards.CONFIG.write("campaign_id", String.valueOf(campaign.data.id));

        // TODO: reconnect websocket with new value
        return 1;
    }
}
