package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.addFriend;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayersSync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.activities.R;
import com.ersfrontend.models.Player;

import java.util.ArrayList;

public class FriendsListFragment extends Fragment implements View.OnClickListener{

    private Button exitButton, searchButton;
    private EditText enteredText;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private Player currPlayer;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        exitButton = view.findViewById(R.id.exitButton_Add_Friends);
        searchButton = view.findViewById(R.id.searchButton_Add_Friends);
        scrollView = view.findViewById(R.id.scrollView_Add_Friends);
        linearLayout = view.findViewById(R.id.linearLayout_Add_Friends);
        enteredText = view.findViewById(R.id.editText_Add_Friends);

        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        exitButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exitButton_Add_Friends:
                FriendsFragment friendsFragment = new FriendsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.friendsFragmentView, friendsFragment);
                fragmentTransaction.commit();
                break;
            case R.id.searchButton_Add_Friends:
                ArrayList<Player> foundPlayers = getPlayersSync();
                String text = enteredText.getText().toString();
                for (Player player : foundPlayers) {
                    if (player.getUsername().contains(text)) {
                        TextView foundPlayer = new TextView(this.getContext());
                        foundPlayer.setText(player.getUsername() + "\n\n");
                        foundPlayer.setTextSize(20);
                        foundPlayer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        foundPlayer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addFriend(currPlayer.getId(), player.getId());
                            }
                        });
                    }
                }
                break;
        }
    }
}