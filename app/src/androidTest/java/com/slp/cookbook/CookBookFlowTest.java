package com.slp.cookbook;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.slp.cookbook.network.NetworkUtils;
import com.slp.cookbook.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Lakshmiprasad on 08-09-2017.
 */

@RunWith(AndroidJUnit4.class)
public class CookBookFlowTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void appFlow(){
        if(NetworkUtils.isNetworkAvailable(activityTestRule.getActivity())) {
            Espresso.onView(ViewMatchers.withText("Brownies")).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withText("Recipe Introduction")).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withId(R.id.step_description)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }else {
            Log.i("appFlow: ","Connect to network and try");
        }

    }

    @Test
    public void appIngredientFlow(){
        if(NetworkUtils.isNetworkAvailable(activityTestRule.getActivity())) {
            Espresso.onView(ViewMatchers.withText("Brownies")).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withId(R.id.ingredients_card)).perform(ViewActions.click());
            Espresso.onView(ViewMatchers.withId(R.id.ingredients)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }else {
            Log.i("appFlow: ","Connect to network and try");
        }

    }

}
