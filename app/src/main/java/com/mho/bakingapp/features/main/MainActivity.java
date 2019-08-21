package com.mho.bakingapp.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.test.espresso.IdlingResource;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.recipe.RecipeListAdapter;
import com.mho.bakingapp.adapters.recipe.RecipeViewHolder;
import com.mho.bakingapp.bases.BaseActivity;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.databinding.ActivityMainBinding;
import com.mho.bakingapp.features.recipedetail.RecipeDetailActivity;
import com.mho.bakingapp.utils.FetchingIdlingResource;
import com.mho.bakingapp.widgets.AppWidgetService;

import java.util.List;

import static com.mho.bakingapp.utils.Constants.EXTRA_RECIPE;
import static com.mho.bakingapp.utils.Constants.RECIPE_GRID_COLUMNS;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator, RecipeViewHolder.OnRecipeViewHolderListener{

    //region Constants

    private final static String TAG = MainActivity.class.getSimpleName();

    //endregion

    //region Fields

    private FetchingIdlingResource idlingResource;

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

        binding.recipeListSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.retryInitRecipeList();
            }
        });

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
        binding.recipeListSwipeRefresh.setRefreshing(false);
        recipeAdapter.setList(recipeList);
        if(idlingResource != null){
            idlingResource.doneFetching();
        }
    }

    @Override
    public void showLoadingRecipeListError(){
        binding.recipeListSwipeRefresh.setRefreshing(false);
        if(idlingResource != null){
            idlingResource.doneFetching();
        }
    }

    @Override
    public void beginFetching() {
        if(idlingResource != null){
            idlingResource.beginFetching();
        }
    }

    @Override
    public void selectRecipe(Recipe recipe) {
        Log.d(TAG, "selectRecipe recipe: " + recipe);
        AppWidgetService.updateWidget(this, recipe);

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    //endregion

    //region Public Methods

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new FetchingIdlingResource();
        }

        return idlingResource;
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