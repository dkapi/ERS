package com.ersfrontend.activities;

import static android.graphics.Color.WHITE;
import static com.ersfrontend.presenters.GamePresenter.getGamesSync;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.services.GameService.joinGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity implements OnClickListener {

    private Player currPlayer;
    private Button exitBtn, searchBtn;
    private EditText gameSearch;
    private ScrollView scrollView;
    private TextView returnedGame;
    private LinearLayout scrollViewLayout;
    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_join);

        exitBtn = findViewById(R.id.JoinGame_ExitBtn);
        searchBtn = findViewById(R.id.JoinGame_SearchBtn);
        gameSearch = findViewById(R.id.SearchGames_JoinScreen);
        scrollView = findViewById(R.id.SearchedGames_JoinScreen);
        scrollViewLayout = findViewById(R.id.ScrollViewLayout_JoinScreen);

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        exitBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    /**
     * Determines next action to be taken based on button click
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case(R.id.JoinGame_ExitBtn):
                startActivity(new Intent(view.getContext(), PlayActivity.class));
                break;
            case(R.id.JoinGame_SearchBtn):
                ArrayList<Game> games = getGamesSync();
                for (Game game : games) {
                    if (game.getName().contains(gameSearch.getText().toString())) {
                        returnedGame = new TextView(this);
                        returnedGame.setText("Game id: " + game.getId() + "\n" + "Game name: " + game.getName() + "\n");
                        returnedGame.setTextSize(20);
                        returnedGame.setBackgroundColor(WHITE);
                        returnedGame.setPadding(0, 2, 0, 2);
                        returnedGame.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        // On Click Listener here so that each individual new text view gets its own unique click
                        returnedGame.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO: This will probably need some work
                                joinGame(game, currPlayer);
                                startActivity(new Intent(getApplicationContext(), WaitingActivity.class));
                            }
                        });
                        scrollViewLayout.addView(returnedGame);
                    }
                }
                break;
        }
    }
}