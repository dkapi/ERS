package com.ersfrontend.slapsockets;

import static com.ersfrontend.Fragments.ChatFragment.setTextBox;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatSocketListener extends WebSocketListener {
    /**
     * Executes when the socket is opened
     * Logs the opening of the socket
     * @param webSocket
     * @param response
     */
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("OPEN", "run() returned: " + "is connecting");
    }

    /**
     * Calls setTextBox(text) when a message is received
     * @param webSocket
     * @param text
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d("MESSAGE", "onMessage() returned: " + text);
        setTextBox(text);
    }

    /**
     *
     * @param webSocket
     * @param bytes
     */
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    /**
     * Logs reason for websocket closing
     * @param webSocket
     * @param code
     * @param reason
     */
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d("CLOSE", "onClosing() returned: " + reason + "Code: " + code);
    }

    /**
     * Logs reason for websocked closed
     * @param webSocket
     * @param code
     * @param reason
     */
    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.d("CLOSE", "onClosed() returned: " + reason + "Code: " + code);
    }

    /**
     * Logs reason for websocket failure
     * @param webSocket
     * @param t
     * @param response
     */
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d("Exception:", t.toString());
    }
}
