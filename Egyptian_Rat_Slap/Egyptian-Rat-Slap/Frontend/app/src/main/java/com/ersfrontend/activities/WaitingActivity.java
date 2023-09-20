package com.ersfrontend.activities;

import static com.ersfrontend.activities.GameActivity.setPlayerOrder;
import static com.ersfrontend.presenters.GamePresenter.getGameByIDSync;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.services.GameService.getGameStoreId;
import static com.ersfrontend.slapsockets.RequestStrings.PLAYER_JOINED;
import static com.ersfrontend.util.StringToArrayList.stringToArrayList;
import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;
import com.ersfrontend.slapsockets.SlapSocketBuilder;

import okhttp3.WebSocket;

public class WaitingActivity extends AppCompatActivity {
    private TextView waitingText;
    private static Activity activity;
    private Player currPlayer;
    private SharedPreferences sharedPreferences;
    private SlapSocketBuilder gameServer;
    private Game currentGame;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        waitingText = findViewById(R.id.WaitingText);

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        currentGame = getGameByIDSync(getGameStoreId());

        gameServer = SlapSocketBuilder.getInstance();
        gameServer.reconnectGame(currentGame.getId(), currPlayer.getUsername());
        gameServer.sendGameMessage(PLAYER_JOINED);

        // Testing something sus
        activity = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameServer.closeGame();
    }

    /**
     * Starts the gameActivity if the playerCount is 4 exactly.
     * Called by the GameSocketListener when a specific message is received.
     * @param message
     */
    public static void checkGameState(String message) throws InterruptedException {
        if (message.charAt(3) == '4') {
            TextView tempView = activity.findViewById(R.id.WaitingText);
            tempView.setText("Starting Game!");
            sleep(3000);
            activity.startActivity(new Intent(activity.getApplicationContext(), GameActivity.class));
        }
    }
}