package com.ersfrontend.activities;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.services.GameService.hostGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activities.R;
import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;

public class CustomActivity extends AppCompatActivity implements OnClickListener {

    private Player currPlayer;
    private TextView textWindow;
    private EditText customGameName;
    private Switch privateToggle;
    private Button createGameBtn, exitBtn;
    private Game customGame = new Game();
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
        setContentView(R.layout.activity_custom);

        textWindow = findViewById(R.id.CustomGame_TextView);
        customGameName = findViewById(R.id.CustomGame_GameName);
        privateToggle = findViewById(R.id.CustomGame_PrivateToggle);
        createGameBtn = findViewById(R.id.CustomGame_Btn);
        exitBtn = findViewById(R.id.CustomGame_ExitBtn);

        sharedPreferences = getApplicationContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        createGameBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
    }

    /**
     * Determines next action to be taken based on button click
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case(R.id.CustomGame_Btn):
                if (customGameName.getText().toString().equals(null) || customGameName.getText().toString().equals("")) {
                    Toast.makeText(CustomActivity.this, "Name is invalid", Toast.LENGTH_SHORT).show();
                    break;
                }
                customGame.setName(customGameName.getText().toString());
                customGame.setPrivateState(privateToggle.isChecked());
                currPlayer.setHost(true);
                // TODO: Have backend update the put player into a game method so that it sets the player to the given player
                hostGame(customGame, currPlayer);
                break;
            case(R.id.CustomGame_ExitBtn):
                startActivity(new Intent(view.getContext(), PlayActivity.class));
                break;
        }
    }
}