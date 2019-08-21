package com.mho.bakingapp.features.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.features.recipestep.RecipeStepActivity;
import com.mho.bakingapp.features.recipestep.RecipeStepFragment;
import com.mho.bakingapp.features.recipesteppage.RecipeStepPageFragment;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.EXTRA_RECIPE;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_ID;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_LIST;

public class RecipeDetailActivity extends AppCompatActivity implements
        RecipeDetailFragment.OnRecipeDetailFragmentListener, RecipeStepFragment.OnRecipeStepFragmentListener {

    //region Fields

    private boolean isTwoPane;

    private int stepId = 0;

    private Recipe recipe;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        isTwoPane = getResources().getBoolean(R.bool.two_pane_mode);

        if(savedInstanceState == null){
            recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
            stepId = getIntent().getIntExtra(EXTRA_STEP_ID, 0);
        }else{
            recipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            stepId = savedInstanceState.getInt(EXTRA_STEP_ID);
        }

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
            RecipeStepPageFragment recipeStepFragment = (RecipeStepPageFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_step_fragment);
            if(recipeStepFragment == null){
                Bundle args = new Bundle();
                args.putParcelable(EXTRA_STEP, getCurrentStep(recipe.getSteps(), stepId));

                recipeStepFragment = RecipeStepPageFragment.newInstance(args);

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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(EXTRA_RECIPE, recipe);
        outState.putInt(EXTRA_STEP_ID, stepId);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startRecipeStep(int stepId, List<Step> stepList) {
        this.stepId = stepId;

        if(isTwoPane){
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_STEP, getCurrentStep(stepList, stepId));

            RecipeStepPageFragment recipeStepFragment = RecipeStepPageFragment.newInstance(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.recipe_step_fragment, recipeStepFragment);
            transaction.commit();

            return;
        }

        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        intent.putParcelableArrayListExtra(EXTRA_STEP_LIST, (ArrayList<Step>) stepList);
        startActivity(intent);
    }

    private Step getCurrentStep(List<Step> stepList, int stepId){
        for(int i=0; i<stepList.size(); i++){
            Step step = stepList.get(i);
            if(step.getId().equals(stepId)){
                return step;
            }
        }

        return stepList.get(0);
    }

    //endregion
}