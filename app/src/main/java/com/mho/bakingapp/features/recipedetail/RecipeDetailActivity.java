package com.mho.bakingapp.features.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.features.recipestep.RecipeStepActivity;
import com.mho.bakingapp.features.recipestep.RecipeStepFragment;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.EXTRA_RECIPE;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_ID;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_LIST;

public class RecipeDetailActivity extends AppCompatActivity implements
        RecipeDetailFragment.OnRecipeDetailFragmentListener, RecipeStepFragment.OnRecipeStepFragmentListener {

    //region Fields

    private boolean isTwoPane;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        isTwoPane = getResources().getBoolean(R.bool.two_pane_mode);

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);
        if(recipeDetailFragment == null){
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_RECIPE, recipe);

            recipeDetailFragment = RecipeDetailFragment.newInstance(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.recipe_detail_fragment, recipeDetailFragment);
            transaction.commit();
        }

        if(isTwoPane){
            RecipeStepFragment recipeStepFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_fragment);
            if(recipeStepFragment == null){
                Bundle args = new Bundle();
                args.putInt(EXTRA_STEP_ID, recipe.getSteps().get(0).getId());
                args.putParcelableArrayList(EXTRA_STEP_LIST, (ArrayList<Step>) recipe.getSteps());

                recipeStepFragment = RecipeStepFragment.newInstance(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.recipe_step_fragment, recipeStepFragment);
                transaction.commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startRecipeStep(int stepId, List<Step> stepList) {
        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        intent.putParcelableArrayListExtra(EXTRA_STEP_LIST, (ArrayList<Step>) stepList);
        startActivity(intent);
    }

    //endregion
}