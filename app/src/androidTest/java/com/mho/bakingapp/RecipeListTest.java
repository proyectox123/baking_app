package com.mho.bakingapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.mho.bakingapp.features.main.MainActivity;
import com.mho.bakingapp.utils.FetchingIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {

    private FetchingIdlingResource fetchingIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        fetchingIdlingResource = (FetchingIdlingResource) activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(fetchingIdlingResource);
    }

    @Test
    public void openRecipeFromRecipeList() {

        onView(withId(R.id.recipeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.recipeIngredientsRecyclerView))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(fetchingIdlingResource);
    }
}