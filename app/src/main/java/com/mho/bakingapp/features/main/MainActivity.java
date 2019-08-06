package com.mho.bakingapp.features.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.recipe.RecipeListAdapter;
import com.mho.bakingapp.adapters.recipe.RecipeViewHolder;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.databinding.ActivityMainBinding;
import com.mho.bakingapp.bases.BaseActivity;

import java.util.List;

import static com.mho.bakingapp.utils.Constants.RECIPE_GRID_COLUMNS;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, RecipeViewHolder.OnRecipeViewHolderListener{

    //region Constants

    private final static String TAG = MainActivity.class.getSimpleName();

    //endregion

    //region Fields

    private RecipeListAdapter recipeAdapter;

    private MainViewModel mainViewModel;

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mainViewModel.validatePaneMode(getResources().getBoolean(R.bool.two_pane_mode));

        recipeAdapter = new RecipeListAdapter(this);
        binding.recipeRecyclerView.setAdapter(recipeAdapter);

        initData(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainViewModel.saveInstanceState(outState);
    }

    @Override
    public int getIdLayout() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public MainViewModel getViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        return mainViewModel;
    }

    @Override
    public void setNavigator() {
        mainViewModel.setNavigator(this);
    }

    @Override
    public void setGridLayoutManager() {
        binding.recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, RECIPE_GRID_COLUMNS));
    }

    @Override
    public void setLinearLayoutManager() {
        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void updateRecipeList(@NonNull List<Recipe> recipeList) {
        recipeAdapter.setList(recipeList);
    }

    @Override
    public void showUpdateRecipeListError() {

    }

    @Override
    public void selectRecipe(Recipe recipe) {
        Log.d(TAG, "selectRecipe recipe: " + recipe);
        Toast.makeText(MainActivity.this, "Recipe " + recipe.toString(), Toast.LENGTH_LONG).show();
    }

    //endregion

    //region Private Methods

    private void initData(Bundle savedInstanceState){
        Log.d(TAG, "initData");
        mainViewModel.getRecipeListData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                mainViewModel.validateRecipeList(recipeList);
            }
        });

        mainViewModel.validateInstanceState(savedInstanceState);
    }

    //endregion
}