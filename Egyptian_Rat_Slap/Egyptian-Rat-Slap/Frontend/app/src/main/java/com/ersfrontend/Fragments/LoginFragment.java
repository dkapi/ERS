package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayersSync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.activities.R;
import com.ersfrontend.activities.MenuActivity;
import com.ersfrontend.models.Player;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private static ArrayList<Player> playersToTestAgainst;
    String username, password;

    public LoginFragment() {
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

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = view.findViewById(R.id.LoginUsername);
        passwordEditText = view.findViewById(R.id.LoginPassword);
        loginButton = view.findViewById(R.id.BtnLogin);

        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        playersToTestAgainst = new ArrayList<>();

        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                boolean isLoggedIn = false;

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getContext(), "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    playersToTestAgainst = getPlayersSync();

                    for (Player player : playersToTestAgainst) {
                        if (username.equals(player.getUsername()) && password.equals(player.getPassword()) && isLoggedIn == false) {
                            sharedPreferences.edit().putInt("Player_Id", player.getId()).apply();
                            startActivity(new Intent(v.getContext(), MenuActivity.class));
                            isLoggedIn = true;
                        }
                    }
                }
            }
        });
        return view;
    }
}

