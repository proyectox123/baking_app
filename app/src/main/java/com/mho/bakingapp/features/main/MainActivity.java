package com.mho.bakingapp.features.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.recipe.RecipeListAdapter;
import com.mho.bakingapp.adapters.recipe.RecipeViewHolder;
import com.mho.bakingapp.databinding.ActivityMainBinding;
import com.mho.bakingapp.features.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.RECIPE_GRID_COLUMNS;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements RecipeViewHolder.OnRecipeViewHolderListener{

    //region Constants

    private final static String TAG = MainActivity.class.getSimpleName();

    //endregion

    //region Fields

    private RecipeListAdapter recipeAdapter;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        validatePaneMode(getResources().getBoolean(R.bool.two_pane_mode));

        recipeAdapter = new RecipeListAdapter(this);
        binding.recipeRecyclerView.setAdapter(recipeAdapter);

        initData();
    }

    @Override
    public int getIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void selectRecipe(String recipe) {
        Log.d(TAG, "selectRecipe recipe: " + recipe);
        Toast.makeText(MainActivity.this, "Recipe " + recipe, Toast.LENGTH_LONG).show();
    }

    //endregion

    //region Private Methods

    private void validatePaneMode(boolean twoPaneMode){
        Log.d(TAG, "validatePaneMode twoPaneMode " + twoPaneMode);
        if(twoPaneMode){
            binding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, RECIPE_GRID_COLUMNS));
        }else {
            binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void initData(){
        Log.d(TAG, "initData");
        List<String> recipeList = new ArrayList<>();
        for(int i = 1; i<=30; i++){
            recipeList.add("Recipe Nº " + i);
        }

        recipeAdapter.setList(recipeList);
    }

    //endregion
}