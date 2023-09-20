package com.ersfrontend;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.activities.R;
import com.ersfrontend.activities.FriendsActivity;
import com.ersfrontend.activities.LeaderboardActivity;
import com.ersfrontend.activities.MenuActivity;
import com.ersfrontend.activities.PlayActivity;
import com.ersfrontend.activities.SettingsActivity;
import com.ersfrontend.activities.StatsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MenuActivityTest {

    @Rule   // needed to launch the activity
    public ActivityScenarioRule<MenuActivity> activityScenarioRule = new ActivityScenarioRule<>(MenuActivity.class);

    @Test
    public void testPlayButton() {
        Intents.init();
        onView(withId(R.id.Play_Button_MenuScreen)).perform(click());
        intended(hasComponent(PlayActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testStatisticsButton() {
        Intents.init();
        onView(withId(R.id.Statistics_Button_MenuScreen)).perform(click());
        intended(hasComponent(StatsActivity.class.getName()));
        Intents.release();

        Intents.init();
        onView(withId(R.id.StatsExitbtn)).perform(click());
        intended(hasComponent(MenuActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testLeaderboardButton() {
        Intents.init();
        onView(withId(R.id.Leaderboard_Button_MenuScreen)).perform(click());
        intended(hasComponent(LeaderboardActivity.class.getName()));
        Intents.release();

        Intents.init();
        onView(withId(R.id.LeaderboardExitbtn)).perform(click());
        intended(hasComponent(MenuActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testSettingsButton() {
        Intents.init();
        onView(withId(R.id.Settings_Button_MenuScreen)).perform(click());
        intended(hasComponent(SettingsActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testFriendsButton() {
        Intents.init();
        onView(withId(R.id.Friends_Button)).perform(click());
        intended(hasComponent(FriendsActivity.class.getName()));
        Intents.release();
    }
}
