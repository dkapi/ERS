package com.ersfrontend.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.activities.R;
import com.ersfrontend.activities.MenuActivity;

public class GameOverFragment extends Fragment {


    public GameOverFragment() {
        // Required empty public constructor
    }

    private Button exitButton;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_over, container, false);

        // Get reference to the exit button
        exitButton = view.findViewById(R.id.GameExtBtn);

        // Set click listener for the exit button
        exitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                // Move the user back to MenuActivity
                startActivity(new Intent(view.getContext(), MenuActivity.class));
            }
        });

        // Show "Game Over" message
        TextView gameOverTextView = view.findViewById(R.id.GameOverText);
        gameOverTextView.setText("Game Over!");

        return view;
    }

}
