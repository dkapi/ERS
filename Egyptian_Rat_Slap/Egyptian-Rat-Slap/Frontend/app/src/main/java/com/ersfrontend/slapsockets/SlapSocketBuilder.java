package com.ersfrontend.slapsockets;

import static com.ersfrontend.util.AppURLs.BASE_URL_SLAPSOCKET;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.internal.ws.WebSocketExtensions;

public class SlapSocketBuilder {
    private static WebSocket gameWebSocket;
    private static WebSocket chatWebSocket;
    private static OkHttpClient client;
    private static String gameURL;
    private static String chatURL;
    private static SlapSocketBuilder instance;

    private SlapSocketBuilder(){
    }

    /**
     * Builds a websocket specified for game servers
     * @param serverID
     * @return webSocket
     */
    public void gameSocketBuilder(int serverID, String username) {
        gameURL = BASE_URL_SLAPSOCKET + "/game/" + serverID + "/" + username;
        client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(gameURL).build();
        GameSocketListener gameSocketListener = new GameSocketListener();
        gameWebSocket = client.newWebSocket(request, gameSocketListener);
        client.dispatcher().executorService().shutdown();
    }

    /**
     * Builds a websocket specified for chat rooms
     * @param username
     * @return webSocket
     */
    public void chatSocketBuilder(String username) {
        chatURL = BASE_URL_SLAPSOCKET + "/globalchat/" + username;
        client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(chatURL).build();
        ChatSocketListener chatSocketListener = new ChatSocketListener();
        chatWebSocket = client.newWebSocket(request, chatSocketListener);
        client.dispatcher().executorService().shutdown();
    }

    public static SlapSocketBuilder getInstance() {
        if (instance == null) {
            instance = new SlapSocketBuilder();
        }
        return instance;
    }
    public boolean isGameConnected() {
        return gameWebSocket != null && gameWebSocket.send("ping");
    }
    public boolean isChatConnected() {
        return chatWebSocket != null && chatWebSocket.send("ping");
    }
    public void reconnectGame(int serverID, String username) {
        if (!isGameConnected()) {
            gameSocketBuilder(serverID, username);
        }
    }
    public void reconnectChat(String username) {
        if (!isChatConnected()) {
            chatSocketBuilder(username);
        }
    }
    public void closeGame() {
        if (gameWebSocket != null) {
            gameWebSocket.close(1000, "User initiated");
        }
    }
    public void closeChat() {
        if (chatWebSocket != null) {
            chatWebSocket.close(1000, "User initiated");
        }
    }
    public void sendGameMessage(String message) {
        if (gameWebSocket != null) {
            gameWebSocket.send(message);
        }
    }
    public void sendChatMessage(String message) {
        if (chatWebSocket != null) {
            chatWebSocket.send(message);
        }
    }
}