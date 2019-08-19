package com.mho.bakingapp.features.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.features.recipestep.RecipeStepActivity;

public class RecipeDetailActivity extends AppCompatActivity implements
        RecipeDetailFragment.OnRecipeDetailFragmentListener {

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);
        if(recipeDetailFragment == null){
            recipeDetailFragment = RecipeDetailFragment.newInstance(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.recipe_detail_fragment, recipeDetailFragment);
            transaction.commit();
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startRecipeStep(Step step) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        startActivity(intent);
    }

    //endregion
}