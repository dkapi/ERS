package com.ersfrontend.slapsockets;

import static com.ersfrontend.activities.GameActivity.setCenterCardImages;
import static com.ersfrontend.activities.GameActivity.setPlayerOrder;
import static com.ersfrontend.activities.GameActivity.successfulSlap;
import static com.ersfrontend.activities.WaitingActivity.checkGameState;
import static com.ersfrontend.slapsockets.RequestStrings.CURRENT_TURN;
import static com.ersfrontend.slapsockets.RequestStrings.PLAYER_JOINED;
import static com.ersfrontend.slapsockets.RequestStrings.SET_CARD;
import static com.ersfrontend.slapsockets.RequestStrings.SLAP;
import static com.ersfrontend.slapsockets.RequestStrings.TURN_ORDER;
import static com.ersfrontend.util.StringToArrayList.stringToArrayList;

import android.util.Log;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class GameSocketListener extends WebSocketListener {

    /**
     * Logs opening of websocket
     *
     * @param webSocket
     * @param response
     */
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("OPEN", "run() returned: " + "is connecting");
    }

    /**
     * @param webSocket
     * @param text
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println(text);
        Log.d("MESSAGE", "onMessage() returned: " + text);
        String code = text.substring(0, 2);
        switch (code) {
            case PLAYER_JOINED:
                try {
                    checkGameState(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case TURN_ORDER:
                ArrayList<Integer> playerOrderIds = stringToArrayList(text.substring(3));
                setPlayerOrder(playerOrderIds);
                break;
            case SLAP:
                successfulSlap();
                break;
            case SET_CARD:
                // Figure ill have the code "41 [arrayList here]"
                String textNoCode = text.substring(3);
                ArrayList<Integer> cardIDs = stringToArrayList(textNoCode);
                setCenterCardImages(cardIDs);
                break;
            case CURRENT_TURN:
                // TODO: add some stuff to this
                textNoCode = text.substring(3);
                break;
        }
    }

    /**
     * @param webSocket
     * @param bytes
     */
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    /**
     * Logs reason for websocket closing
     *
     * @param webSocket
     * @param code
     * @param reason
     */
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d("CLOSE", "onClosing() returned: " + reason + "Code: " + code);
    }

    /**
     * Logs reason for websocket closed
     *
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
     *
     * @param webSocket
     * @param t
     * @param response
     */
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d("Exception:", t.toString());
    }
}