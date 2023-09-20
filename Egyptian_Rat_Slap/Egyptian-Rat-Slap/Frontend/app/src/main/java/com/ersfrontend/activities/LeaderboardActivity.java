package com.ersfrontend.activities;

import static com.ersfrontend.presenters.PlayerPresenter.getTopTenPlayersSync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Player;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private Button LeaderboardExitbtn;
    private TextView leaderboardText;
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
        setContentView(R.layout.activity_leaderboard);

        leaderboardText = findViewById(R.id.leaderboardText);
        LeaderboardExitbtn = findViewById(R.id.LeaderboardExitbtn);

        ArrayList<Player> players = getTopTenPlayersSync();

        String leaderboard = "";
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            leaderboard = leaderboard + (i + 1) + ":  " + player.getUsername() + " - " + player.getGamesWon();
            leaderboard = leaderboard + "\n";
        }

        leaderboardText.setText(leaderboard);
        LeaderboardExitbtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MenuActivity.class));
            }
        });
    }
}
