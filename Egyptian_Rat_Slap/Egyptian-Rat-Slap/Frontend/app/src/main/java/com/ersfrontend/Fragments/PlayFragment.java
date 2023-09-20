package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.services.GameService.startGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.activities.R;
import com.ersfrontend.activities.CustomActivity;
import com.ersfrontend.activities.JoinActivity;
import com.ersfrontend.activities.MenuActivity;
import com.ersfrontend.activities.WaitingActivity;
import com.ersfrontend.models.Player;

public class PlayFragment extends Fragment implements View.OnClickListener {

    private Button createGameButton, backButton, joinGameButton, customGameButton, chatButton;
    private SharedPreferences sharedPreferences;
    private Player currPlayer;

    public PlayFragment() {
    }

    /**
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_play, container, false);

        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        currPlayer = getPlayerByIDSync(sharedPreferences.getInt("Player_Id", 0));

        createGameButton = view.findViewById(R.id.QuickPlay_PlayScreen);
        backButton = view.findViewById(R.id.BackButton_PlayScreen);
        joinGameButton = view.findViewById(R.id.Join_PlayScreen);
        customGameButton = view.findViewById(R.id.Custom_PlayScreen);
        chatButton = view.findViewById(R.id.chat_PlayScreen);

        createGameButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        joinGameButton.setOnClickListener(this);
        customGameButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);

        return view;
    }

    /**
     * Determines next action to be taken based on button click
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QuickPlay_PlayScreen:
                startGame(currPlayer);
                startActivity(new Intent(v.getContext(), WaitingActivity.class));
                break;
            case R.id.BackButton_PlayScreen:
                startActivity(new Intent(v.getContext(), MenuActivity.class));
                break;
            case R.id.Join_PlayScreen:
                startActivity(new Intent(v.getContext(), JoinActivity.class));
                break;
            case R.id.Custom_PlayScreen:
                startActivity(new Intent(v.getContext(), CustomActivity.class));
                break;
            case R.id.chat_PlayScreen:
                ChatFragment chatFragment = new ChatFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.PlayFragmentView, chatFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}