package com.ersfrontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.activities.R;
import com.ersfrontend.Fragments.LoginFragment;
import com.ersfrontend.Fragments.SignUpFragment;

public class LoginActivity extends AppCompatActivity {
    private Button endbtn;

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
        setContentView(R.layout.activity_login);

        Button loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                openLoginFragment();
            }

            /**
             * Opens a login fragment
             */
            private void openLoginFragment() {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, loginFragment);
                fragmentTransaction.commit();
            }
        });

        Button signupbtn = findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                openSignUpFragment();
            }

            /**
             * Opens a signup fragment
             */
            private void openSignUpFragment() {
                SignUpFragment signupFragment = new SignUpFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, signupFragment);
                fragmentTransaction.commit();
            }
        });

        endbtn = findViewById(R.id.endbtn);
        endbtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Determines next action to be taken based on button click
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });
    }
}
