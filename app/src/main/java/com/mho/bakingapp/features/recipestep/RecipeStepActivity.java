package com.mho.bakingapp.features.recipestep;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mho.bakingapp.R;

public class RecipeStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        RecipeStepFragment recipeDetailFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_fragment);
        if(recipeDetailFragment == null){
            recipeDetailFragment = RecipeStepFragment.newInstance(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.recipe_step_fragment, recipeDetailFragment);
            transaction.commit();
        }
    }
}
