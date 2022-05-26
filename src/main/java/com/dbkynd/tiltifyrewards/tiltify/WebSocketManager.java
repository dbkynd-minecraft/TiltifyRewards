package com.dbkynd.tiltifyrewards.tiltify;

import com.dbkynd.tiltifyrewards.TiltifyRewards;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class WebSocketManager {
    static Timer timer = new Timer();
    private static String tiltify_id = "146066";
    static WebSocket ws = new WebSocket(URI.create("wss://websockets.tiltify.com/socket/websocket?vsn=2.0.0"));

    static int count = 17;

    public static void connect() {
        TiltifyRewards.LOGGER.info("CONNECTING TO WS");
        ws.connect();
        while(!ws.isOpen()) {}
        ws.send("[\"" + count + "\",\"" + count + "\",\"campaign." + tiltify_id + ".campaign\",\"phx_join\",{}]");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                ws.send("[null,\"" + count + "\",\"phoenix\",\"heartbeat\",{}]");
            }
        }, 30000, 30000);//wait 0 ms before doing the action and do it evry 1000ms (1second)
    }

    public static WebSocket getWs() {
        return ws;
    }
}
