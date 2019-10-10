package com.example.airporter.MainActivity;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import com.example.airporter.Menu.MenuActivity;
import com.example.airporter.R;
import com.example.airporter.helper.CONSTANTS;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.*;

@RunWith(JUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkEmailEditTextError(){
        onView(withId(R.id.signUpEmaileditTextId)).perform(typeText("abc")).check(matches(hasErrorText(CONSTANTS.DisplayMessages.ERROR_EMAIL_INVALID)));
    }

    @Test
    public void checkLogin(){
        onView(withId(R.id.loginButtonId)).check(matches(not(isEnabled())));
        onView(withId(R.id.signUpEmaileditTextId)).perform(ViewActions.typeText("shikkhar.pahwa@hotmail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signUpPasswordEditTextId)).perform((typeText("SMM12792!!")), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButtonId)).check(matches(isEnabled()));
        onView(withId(R.id.loginButtonId)).perform(click());
        intended(hasComponent(MenuActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
    }
}