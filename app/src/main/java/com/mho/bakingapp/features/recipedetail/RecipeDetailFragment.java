package com.mho.bakingapp.features.recipedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.ingredient.IngredientListAdapter;
import com.mho.bakingapp.adapters.recipe.RecipeListAdapter;
import com.mho.bakingapp.adapters.step.StepListAdapter;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.databinding.FragmentRecipeDetailBinding;

import static com.mho.bakingapp.utils.Constants.EXTRA_RECIPE;

public class RecipeDetailFragment extends BaseFragment<FragmentRecipeDetailBinding, RecipeDetailViewModel>
        implements RecipeDetailNavigator{

    //region Constructor

    public RecipeDetailFragment(){ }

    //endregion

    //region Fields

    private Recipe recipe;

    private RecipeDetailViewModel recipeDetailViewModel;

    //endregion

    //region Override Methods & Callbacks


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            if(getArguments().containsKey(EXTRA_RECIPE)){
                recipe = getArguments().getParcelable(EXTRA_RECIPE);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IngredientListAdapter ingredientAdapter = new IngredientListAdapter();
        binding.recipeIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeIngredientsRecyclerView.setAdapter(ingredientAdapter);

        StepListAdapter stepAdapter = new StepListAdapter();
        DividerItemDecoration dividerDecoration = new DividerItemDecoration(binding.recipeStepsRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        binding.recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeStepsRecyclerView.setAdapter(stepAdapter);
        binding.recipeStepsRecyclerView.addItemDecoration(dividerDecoration);

        if(recipe != null){
            ingredientAdapter.setList(recipe.getIngredients());
            stepAdapter.setList(recipe.getSteps());
        }
    }

    @Override
    public int getIdLayout() {
        return R.layout.fragment_recipe_detail;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RecipeDetailViewModel getViewModel() {
        recipeDetailViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        return recipeDetailViewModel;
    }

    @Override
    public void setNavigator() {
        recipeDetailViewModel.setNavigator(this);
    }

    //endregion

    //region Public Methods

    public static RecipeDetailFragment newInstance(Bundle bundle){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion
}