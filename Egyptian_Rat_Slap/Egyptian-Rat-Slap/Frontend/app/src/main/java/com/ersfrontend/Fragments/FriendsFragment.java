package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.activities.R;
import com.ersfrontend.activities.MenuActivity;
import com.ersfrontend.models.Player;

public class FriendsFragment extends Fragment implements View.OnClickListener{

    private Button exitButton, addFriendsButton;
    private ScrollView scrollViewFriends;
    private LinearLayout linearLayoutFriends;
    private Player currPlayer;
    private SharedPreferences sharedPreferences;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        exitButton = view.findViewById(R.id.exit_button_friends_list);
        addFriendsButton = view.findViewById(R.id.add_friends);
        scrollViewFriends = view.findViewById(R.id.Friends_List);
        linearLayoutFriends = view.findViewById(R.id.Friends_List_Layout);

        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        int currPlayerId = sharedPreferences.getInt("Player_Id", 0);
        currPlayer = getPlayerByIDSync(currPlayerId);

        exitButton.setOnClickListener(this);
        addFriendsButton.setOnClickListener(this);
        if (currPlayer.getFriends() != null) {
            for (Player player : currPlayer.getFriends()) {
                TextView friend = new TextView(this.getContext());
                friend.setText(player.getUsername() + "\n\n");
                friend.setTextSize(20);
                friend.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayoutFriends.addView(friend);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_button_friends_list:
                startActivity(new Intent(v.getContext(), MenuActivity.class));
                break;
            case R.id.add_friends:
                FriendsListFragment friendsListFragment = new FriendsListFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.friendsFragmentView, friendsListFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}