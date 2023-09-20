package com.ersfrontend;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayersSync;
import static org.junit.Assert.assertEquals;

import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.activities.R;
import com.ersfrontend.activities.LoginActivity;
import com.ersfrontend.activities.MainActivity;
import com.ersfrontend.activities.MenuActivity;
import com.ersfrontend.models.Player;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void postUserForLogin() {
        String testUsername = "testLogin";
        String testPassword = "testLogin";

        onView(withId(R.id.signupbtn)).perform(click());
        onView(withId(R.id.signupusername)).perform(typeText(testUsername));
        onView(withId(R.id.signuppassword)).perform(typeText(testPassword));
        closeSoftKeyboard();
        onView(withId(R.id.SignUpBtn)).perform(click());
    }

    @Test
    public void changeBetweenLoginAndSignup() {
        onView(withId(R.id.signupbtn)).perform(click());
        onView(withId(R.id.fragmentContainer)).check(matches(isDisplayed()));
        onView(withId(R.id.loginbtn)).perform(click());
        onView(withId(R.id.fragmentContainer)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpUser() {
        String testUsername = "testSignup";
        String testPassword = "testSignup";

        onView(withId(R.id.signupbtn)).perform(click());
        onView(withId(R.id.signupusername)).perform(typeText(testUsername));
        onView(withId(R.id.signuppassword)).perform(typeText(testPassword));
        closeSoftKeyboard();
        onView(withId(R.id.SignUpBtn)).perform(click());
        ArrayList<Player> players = getPlayersSync();
        for (Player player : players) {
            if (player.getUsername().equals(testUsername) && player.getPassword().equals(testPassword)) {
                assertEquals(player.getUsername(), testUsername);
                assertEquals(player.getPassword(), testPassword);
            }
        }
    }

    @Test
    public void testLoginWithUser() {
        String testUsername = "testLogin";
        String testPassword = "testLogin";

        onView(withId(R.id.loginbtn)).perform(click());
        onView(withId(R.id.LoginUsername)).perform(typeText(testUsername));
        onView(withId(R.id.LoginPassword)).perform(typeText(testPassword));
        closeSoftKeyboard();
        Intents.init();
        onView(withId(R.id.BtnLogin)).perform(click());
        intended(hasComponent(MenuActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testInvalidLogin() {
        String testUsername = "";
        String testPassword = "";
        String testValidUsername = "test";
        String testValidPassword = "test";

        String testWrongUsername = "testLoginWrong";
        String testWrongPassword = "testLoginWrong";

        Intents.init();
        onView(withId(R.id.loginbtn)).perform(click());
        onView(withId(R.id.LoginUsername)).perform(typeText(testUsername));
        onView(withId(R.id.LoginPassword)).perform(typeText(testPassword));
        closeSoftKeyboard();
        onView(withId(R.id.BtnLogin)).perform(click());
        assertEquals(0, Intents.getIntents().size());
        Intents.release();

        Intents.init();
        onView(withId(R.id.LoginUsername)).perform(replaceText(testUsername));
        onView(withId(R.id.LoginPassword)).perform(replaceText(testValidPassword));
        closeSoftKeyboard();
        onView(withId(R.id.BtnLogin)).perform(click());
        assertEquals(0, Intents.getIntents().size());
        Intents.release();

        Intents.init();
        onView(withId(R.id.LoginUsername)).perform(replaceText(testValidUsername));
        onView(withId(R.id.LoginPassword)).perform(replaceText(testPassword));
        closeSoftKeyboard();
        onView(withId(R.id.BtnLogin)).perform(click());
        assertEquals(0, Intents.getIntents().size());
        Intents.release();

        Intents.init();
        onView(withId(R.id.LoginUsername)).perform(replaceText(testWrongUsername));
        onView(withId(R.id.LoginPassword)).perform(replaceText(testValidPassword));
        closeSoftKeyboard();
        onView(withId(R.id.BtnLogin)).perform(click());
        assertEquals(0, Intents.getIntents().size());
        Intents.release();

        Intents.init();
        onView(withId(R.id.LoginUsername)).perform(replaceText(testValidUsername));
        onView(withId(R.id.LoginPassword)).perform(replaceText(testWrongPassword));
        closeSoftKeyboard();
        onView(withId(R.id.BtnLogin)).perform(click());
        assertEquals(0, Intents.getIntents().size());
        Intents.release();
    }

    @Test
    public void testBackButton() {
        Intents.init();
        onView(withId(R.id.endbtn)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }
}
