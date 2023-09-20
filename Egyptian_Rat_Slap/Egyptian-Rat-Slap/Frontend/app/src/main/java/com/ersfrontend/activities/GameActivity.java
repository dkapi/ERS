package com.ersfrontend.activities;

import static com.ersfrontend.presenters.GamePresenter.deleteGame;
import static com.ersfrontend.presenters.GamePresenter.getGameByIDSync;
import static com.ersfrontend.presenters.GamePresenter.getGamesSync;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.services.GameService.gameEnded;
import static com.ersfrontend.services.GameService.getCenterPileImages;
import static com.ersfrontend.services.GameService.getGameStoreId;
import static com.ersfrontend.services.GameService.setCardCenterPile;
import static com.ersfrontend.services.GameService.setUpGame;
import static com.ersfrontend.services.GameService.slap;
import static com.ersfrontend.slapsockets.RequestStrings.SET_CARD;
import static com.ersfrontend.slapsockets.RequestStrings.SLAP;
import static com.ersfrontend.slapsockets.RequestStrings.TURN_ORDER;
import static com.ersfrontend.util.GameRules.setSlappable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;
import com.ersfrontend.slapsockets.SlapSocketBuilder;
import com.ersfrontend.util.SlapQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements OnClickListener {
    private ImageButton playerLeft;
    private ImageButton playerRight;
    private ImageButton playerTop;
    private static ImageButton playerBottom;
    private static ImageButton centerPileFront;
    private static TextView turnView;
    private static ImageView centerPileBack, centerPileMiddle;
    private static Player currPlayer;
    private static Game currentGame;
    private static SlapQueue<Integer> gameQueue, playerBQueue;
    private SharedPreferences sharedPreferences;
    private ArrayList<Integer> cardImageIds;
    private static SlapSocketBuilder gameServer;
    private static int currentTurn;
    private static ArrayList<Player> playerOrder;
    private static Map<Integer, ArrayList<Integer>> playerHands;
    private static GameActivity gameActivityInstance = new GameActivity();
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
        setContentView(R.layout.activity_game);

        playerLeft = findViewById(R.id.ImageButton_PlayerLeft);
        playerRight = findViewById(R.id.ImageButton_PlayerRight);
        playerTop = findViewById(R.id.ImageButton_PlayerTop);
        playerBottom = findViewById(R.id.ImageButton_PlayerBottom);
        turnView = findViewById(R.id.GameActivityTurnView);

        centerPileBack = findViewById(R.id.CenterPileBack);
        centerPileMiddle = findViewById(R.id.CenterPileMiddle);
        centerPileFront = findViewById(R.id.CenterPileFront);

        currentGame = getGameByIDSync(getGameStoreId());

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        gameServer = SlapSocketBuilder.getInstance();
        gameServer.reconnectGame(currentGame.getId(), currPlayer.getUsername());
        playerOrder = new ArrayList<>();
        gameServer.sendGameMessage(TURN_ORDER);

        gameQueue = new SlapQueue<>();
        playerBottom.setOnClickListener(this);
        centerPileFront.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        gameServer.closeGame();
        deleteGame(currentGame.getId());
    }
    /**
     * Determines next action to be taken based on button click
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CenterPileFront:
                // TODO: Send message to backend and backend will send message to frontend about who's queue it should be
                gameServer.sendGameMessage(SLAP);
                centerPileFront.setClickable(setSlappable(gameQueue));
                if (gameEnded(currPlayer)) {
                    startActivity(new Intent(v.getContext(), PlayActivity.class));
                }
                break;
            case R.id.ImageButton_PlayerBottom:
                // When clicked send to the backend the id so that other players can update their game
                gameQueue = setCardCenterPile(gameQueue, playerBQueue);
                cardImageIds = getCenterPileImages(gameQueue);

                gameServer.sendGameMessage(SET_CARD + " " + cardImageIds);
                break;
        }
    }
    /**
     * Updates the turn counter and decides if it is the current user's turn
     */
    private static void updateTurns() {
        currentTurn = (currentTurn + 1) % 4;
        playerBottom.setClickable(playerOrder.get(currentTurn).getUsername().equals(currPlayer.getUsername()));
        turnView.setText(playerOrder.get(currentTurn).getUsername());
    }
    public static void successfulSlap() {
        playerBQueue = slap(playerBQueue, gameQueue);
    }
    public static void setCenterCardImages(ArrayList<Integer> cardImageIds) {
        gameActivityInstance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (cardImageIds.size()) {
                    case 1:
                        centerPileFront.setImageResource(cardImageIds.get(0));
                        break;
                    case 2:
                        centerPileMiddle.setImageResource(cardImageIds.get(1));
                        centerPileFront.setImageResource(cardImageIds.get(0));
                        break;
                }
                if (cardImageIds.size() >= 3) {
                    centerPileBack.setImageResource(cardImageIds.get(0));
                    centerPileMiddle.setImageResource(cardImageIds.get(1));
                    centerPileFront.setImageResource(cardImageIds.get(2));
                }
                centerPileFront.setClickable(setSlappable(gameQueue));
            }
        });
        updateTurns();
    }
    public static void setPlayerOrder(ArrayList<Integer> playerOrderIds) {
        ArrayList<Player> temp = new ArrayList<>();
        for (int i = 0; i < playerOrderIds.size(); ++i) {
            temp.add(getPlayerByIDSync(playerOrderIds.get(i)));
        }
        playerOrder.addAll(temp);
        playerHands = setUpGame(currentGame);

        // Give the player their hand
        for(int i = 0; i < 4; ++i) {
            if (playerOrder.get(i).getUsername().equals(currPlayer.getUsername())) {
                currPlayer.setPlayerHand(playerHands.get(i));
            }
        }
        playerBQueue = new SlapQueue<>(currPlayer.getPlayerHand());
        centerPileFront.setClickable(false);
        currentTurn = -1;
        updateTurns();
    }
}