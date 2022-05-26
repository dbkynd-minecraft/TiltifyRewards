package com.dbkynd.tiltifyrewards.tiltify;

import com.dbkynd.tiltifyrewards.TiltifyRewards;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class WebSocketManager {
    static String wsURL = "wss://websockets.tiltify.com/socket/websocket?vsn=2.0.0";
    static Timer pingTimer = new Timer();
    static Timer reconnectTimer = new Timer();
    static WebSocket ws = new WebSocket(URI.create(wsURL));
    static int count = 17;

    public static void connect() {
        String campaignId = TiltifyRewards.CONFIG.read.getString("campaign_id");
        if (campaignId == null || campaignId.isEmpty()) {
            TiltifyRewards.LOGGER.warn("No campaign id is set");
            return;
        }

        if (ws.isOpen()) {
            TiltifyRewards.LOGGER.info("Closing existing Tiltify WS connection");
            reconnectTimer.cancel();
            ws.close();
            ws = new WebSocket(URI.create(wsURL));
        }

        TiltifyRewards.LOGGER.info("CONNECTING TO WS - Campaign ID: " + campaignId);
        ws.connect();
        while(!ws.isOpen()) {}
        ws.send("[\"" + count + "\",\"" + count + "\",\"campaign." + campaignId + ".campaign\",\"phx_join\",{}]");
        pingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ws.isOpen()) {
                    count++;
                    ws.send("[null,\"" + count + "\",\"phoenix\",\"heartbeat\",{}]");
                }
            }
        }, 30000, 30000);

        reconnectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!ws.isOpen()) {
                    connect();
                }
            }
        }, 10000, 10000);
    }

    public static WebSocket getWs() {
        return ws;
    }
}
