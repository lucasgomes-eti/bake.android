package com.example.android.bake;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bake.ui.IngredientsWidgetConfigActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class IngredientsWidgetConfigActivityTest {

    @SuppressWarnings("unchecked")
    @Rule
    public final IntentsTestRule mActivityTestRule = new IntentsTestRule(IngredientsWidgetConfigActivity.class, false, false);

    @Test
    public void test_recipe_items() {
        mActivityTestRule.launchActivity(new Intent());

        for (int i = 0; i < 3; i++) {
            onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_recipes).atPosition(i))
                    .check(matches(hasDescendant(withId(R.id.tv_item_widget))));
        }
    }

    @Test
    public void test_result() {
        mActivityTestRule.launchActivity(new Intent());

        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_recipes).atPosition(0))
                .perform(click());

        //noinspection ResultOfMethodCallIgnored
        intending(hasExtraWithKey(AppWidgetManager.EXTRA_APPWIDGET_ID));
    }


}
