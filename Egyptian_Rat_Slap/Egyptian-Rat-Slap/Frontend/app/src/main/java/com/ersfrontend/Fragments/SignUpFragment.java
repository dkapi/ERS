package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.postPlayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.activities.R;
import com.ersfrontend.models.Player;

public class SignUpFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    public SignUpFragment() {
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

    private EditText username;
    private EditText password;

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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);

        username = view.findViewById(R.id.signupusername);
        password = view.findViewById(R.id.signuppassword);
        Button signupButton = view.findViewById(R.id.SignUpBtn);

        signupButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String username = SignUpFragment.this.username.getText().toString();
                String password = SignUpFragment.this.password.getText().toString();

                Player newPlayer = new Player();
                newPlayer.setUsername(username);
                newPlayer.setPassword(password);
                postPlayer(newPlayer);
                // Set up storing the player
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Might not know what the id is, could return 0 everytime
                editor.putInt("Player_id", newPlayer.getId());
                editor.commit();
            }
        });
        return view;
    }
}

