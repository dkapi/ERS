package com.ersfrontend.activities;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Player;

public class MenuActivity extends AppCompatActivity  implements View.OnClickListener {

    private Player currPlayer;
    private SharedPreferences sharedPreferences;
    Button playButton, statisticsButton, leaderboardButton, settingsButton, friendsButton;

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
        setContentView(R.layout.activity_menu);

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        playButton = findViewById(R.id.Play_Button_MenuScreen);
        statisticsButton = findViewById(R.id.Statistics_Button_MenuScreen);
        leaderboardButton = findViewById(R.id.Leaderboard_Button_MenuScreen);
        settingsButton = findViewById(R.id.Settings_Button_MenuScreen);
        friendsButton = findViewById(R.id.Friends_Button);

        playButton.setOnClickListener(this);
        statisticsButton.setOnClickListener(this);
        leaderboardButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        friendsButton.setOnClickListener(this);
    }

    /**
     * Determines next action to be taken based on button click
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Play_Button_MenuScreen:
                startActivity(new Intent(v.getContext(), PlayActivity.class));
                break;
            case R.id.Statistics_Button_MenuScreen:
                startActivity(new Intent(v.getContext(), StatsActivity.class));
                break;
            case R.id.Leaderboard_Button_MenuScreen:
                startActivity(new Intent(v.getContext(), LeaderboardActivity.class));
                break;
            case R.id.Settings_Button_MenuScreen:
                startActivity(new Intent(v.getContext(), SettingsActivity.class));
                break;
            case R.id.Friends_Button:
                startActivity(new Intent(v.getContext(), FriendsActivity.class));
                break;
        }
    }
}