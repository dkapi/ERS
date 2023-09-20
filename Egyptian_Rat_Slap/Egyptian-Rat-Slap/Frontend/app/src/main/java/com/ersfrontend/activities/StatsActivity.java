package com.ersfrontend.activities;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Player;

public class StatsActivity extends AppCompatActivity {
    private TextView statsText;
    private Button StatsExitBtn;
    private SharedPreferences sharedPreferences;
    private Player currPlayer;

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
        setContentView(R.layout.activity_stats);

        statsText = findViewById(R.id.statsText);
        StatsExitBtn = findViewById(R.id.StatsExitbtn);

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        String stats = "Name: " + currPlayer.getUsername() + "\n" + "Wins: " + currPlayer.getGamesWon();
        statsText.setText(stats);

        StatsExitBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatsActivity.this, MenuActivity.class));
            }
        });
    }
}