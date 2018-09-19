package com.example.android.bake;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bake.helper.AndroidExtensions;
import com.example.android.bake.model.Ingredient;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.model.Step;
import com.example.android.bake.ui.RecipeDetailActivity;
import com.example.android.bake.ui.RecipesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public IntentsTestRule mActivityTestRule = new IntentsTestRule(RecipeDetailActivity.class, false, false);

    private static Recipe RECIPE_TEST = new Recipe(2, "Brownies", new ArrayList<Ingredient>() {{
        add(new Ingredient("350", "G", "Bittersweet chocolate (60-70% cacao)"));
    }}, new ArrayList<Step>() {{
        add(new Step(0, "Recipe Introduction", "Recipe Introduction",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4",
                "", false));
    }}, 8, 2131099733);

    private static Recipe RECIPE_TEST_NO_VIDEO = new Recipe(2, "Brownies", new ArrayList<Ingredient>() {{
        add(new Ingredient("350", "G", "Bittersweet chocolate (60-70% cacao)"));
    }}, new ArrayList<Step>() {{
        add(new Step(0, "Recipe Introduction", "Recipe Introduction",
                "",
                "", false));
    }}, 8, 2131099733);

    @Test
    public void test_ingredients() {
        Intent intent = new Intent();
        intent.putExtra(RecipesActivity.RECIPE_KEY, RECIPE_TEST);

        mActivityTestRule.launchActivity(intent);

        onView(RecyclerViewMatcher.withRecyclerView(R.id.item_step_list).atPosition(0))
                .check(matches(hasDescendant(withId(R.id.tv_ingredients_title))));

        onView(RecyclerViewMatcher.withRecyclerView(R.id.item_step_list).atPosition(0))
                .check(matches(hasDescendant(withText(RECIPE_TEST.getIngredients().get(0).getQuantity() + " " +
                        RECIPE_TEST.getIngredients().get(0).getMeasure() + " " +
                        RECIPE_TEST.getIngredients().get(0).getIngredient() + "\n"))));
    }

    @Test
    public void test_steps() {
        Intent intent = new Intent();
        intent.putExtra(RecipesActivity.RECIPE_KEY, RECIPE_TEST);

        mActivityTestRule.launchActivity(intent);

        onView(RecyclerViewMatcher.withRecyclerView(R.id.item_step_list).atPosition(1))
                .check(matches(hasDescendant(withText("1"))));

        onView(RecyclerViewMatcher.withRecyclerView(R.id.item_step_list).atPosition(1))
                .check(matches(hasDescendant(withText(RECIPE_TEST.getSteps().get(0).getShortDescription()))));
    }

    @Test
    public void test_video() {
        Intent intent = new Intent();
        intent.putExtra(RecipesActivity.RECIPE_KEY, RECIPE_TEST);

        mActivityTestRule.launchActivity(intent);

        if (AndroidExtensions.getIsTablet(mActivityTestRule.getActivity().getApplication().getApplicationContext())) {

            onView(withId(R.id.playerView))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void test_video_no_url() {
        Intent intent = new Intent();
        intent.putExtra(RecipesActivity.RECIPE_KEY, RECIPE_TEST_NO_VIDEO);

        mActivityTestRule.launchActivity(intent);

        if (AndroidExtensions.getIsTablet(mActivityTestRule.getActivity().getApplication().getApplicationContext())) {

            onView(withId(R.id.playerView))
                    .check(matches(not(isDisplayed())));
        }
    }
}
