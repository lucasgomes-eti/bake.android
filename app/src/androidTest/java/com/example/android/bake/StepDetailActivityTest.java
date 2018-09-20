package com.example.android.bake;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.android.bake.helper.AndroidExtensions;
import com.example.android.bake.model.Step;
import com.example.android.bake.ui.RecipeDetailActivity;
import com.example.android.bake.ui.StepDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    @SuppressWarnings("unchecked")
    @Rule
    public final IntentsTestRule mActivityTestRule = new IntentsTestRule(StepDetailActivity.class, false, false);

    private static final Step STEP_TEST = new Step(0, "Recipe Introduction", "Recipe Introduction",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4",
            "", false);

    private static final Step STEP_TEST_NO_VIDEO = new Step(0, "Recipe Introduction", "Recipe Introduction",
            "",
            "", false);

    @Test
    public void test_video() {

        Intent intent = new Intent();
        intent.putExtra(RecipeDetailActivity.STEP_KEY, STEP_TEST);

        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.playerView))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_video_no_url() {

        Intent intent = new Intent();
        intent.putExtra(RecipeDetailActivity.STEP_KEY, STEP_TEST_NO_VIDEO);

        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.playerView))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void test_video_full_screen() throws Exception {

        Intent intent = new Intent();
        intent.putExtra(RecipeDetailActivity.STEP_KEY, STEP_TEST);

        mActivityTestRule.launchActivity(intent);

        if (!AndroidExtensions.getIsTablet(mActivityTestRule.getActivity().getApplication().getApplicationContext())) {

            mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            Thread.sleep(1000);

            int flags = mActivityTestRule.getActivity().getWindow().getDecorView().getSystemUiVisibility();

            int flagsAssertion = View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (flags != flagsAssertion) {
                throw new IllegalStateException("Current activity is not in full screen mode");
            }
        }
    }
}