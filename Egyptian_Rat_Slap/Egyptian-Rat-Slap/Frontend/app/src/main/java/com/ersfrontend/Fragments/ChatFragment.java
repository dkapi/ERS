package com.ersfrontend.Fragments;

import static com.ersfrontend.presenters.PlayerPresenter.getPlayerByIDSync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.activities.R;
import com.ersfrontend.models.Player;
import com.ersfrontend.slapsockets.SlapSocketBuilder;

public class ChatFragment extends Fragment {

    private static TextView chatBox;
    private EditText messageBox;
    private Button exitButton;
    private SlapSocketBuilder chatSocket;
    private SharedPreferences sharedPreferences;
    private Player currPlayer;

    public ChatFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        sharedPreferences = getContext().getSharedPreferences("Players", Context.MODE_PRIVATE);
        currPlayer = getPlayerByIDSync(sharedPreferences.getInt("Player_Id", 0));

        chatSocket = SlapSocketBuilder.getInstance();
        chatSocket.chatSocketBuilder(currPlayer.getUsername());

        chatBox = view.findViewById(R.id.chatBox);
        messageBox = view.findViewById(R.id.messageBox);
        exitButton = view.findViewById(R.id.exitButton);

        messageBox.setFocusableInTouchMode(true);
        messageBox.requestFocus();
        messageBox.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Executes the code if the enter key is clicked
             * @param v The view the key has been dispatched to.
             * @param keyCode The code for the physical key that was pressed
             * @param event The KeyEvent object containing full information about
             *        the event.
             * @return keyPressStatus
             */
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean keyPressStatus = false;
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    chatSocket.sendChatMessage(messageBox.getText().toString());
                    keyPressStatus = true;
                }
                return keyPressStatus;
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                PlayFragment playFragment = new PlayFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.PlayFragmentView, playFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatSocket.closeChat();
    }

    /**
     * Sets the text displayed in the textbox
     * @param message
     */
    public static void setTextBox(String message) {
        String temp = chatBox.getText().toString();
        chatBox.setText(temp + "\nServer" + message);
    }
}