package com.mho.bakingapp.features.recipedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.ingredient.IngredientListAdapter;
import com.mho.bakingapp.adapters.step.StepListAdapter;
import com.mho.bakingapp.adapters.step.StepViewHolder;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.data.remote.models.Ingredient;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.databinding.FragmentRecipeDetailBinding;

import java.util.List;

public class RecipeDetailFragment extends BaseFragment<FragmentRecipeDetailBinding, RecipeDetailViewModel>
        implements RecipeDetailNavigator, StepViewHolder.OnStepViewHolderListener{

    //region Fields

    private IngredientListAdapter ingredientAdapter;
    private StepListAdapter stepListAdapter;

    private RecipeDetailViewModel recipeDetailViewModel;

    private OnRecipeDetailFragmentListener onRecipeDetailFragmentListener;

    //endregion

    //region Constructor

    public RecipeDetailFragment(){ }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onRecipeDetailFragmentListener = (OnRecipeDetailFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeDetailFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeDetailViewModel.validateRecipeDetailArguments(getArguments());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredientAdapter = new IngredientListAdapter();
        binding.recipeIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeIngredientsRecyclerView.setAdapter(ingredientAdapter);

        stepListAdapter = new StepListAdapter(this);
        binding.recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeStepsRecyclerView.setAdapter(stepListAdapter);

        recipeDetailViewModel.initRecipeIngredientList();
        recipeDetailViewModel.initRecipeStepList();
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

    @Override
    public void finishActivity() {
        onRecipeDetailFragmentListener.finishActivity();
    }

    @Override
    public void updateIngredientList(List<Ingredient> ingredients) {
        ingredientAdapter.setList(ingredients);
    }

    @Override
    public void updateStepList(List<Step> steps) {
        stepListAdapter.setList(steps);
    }

    @Override
    public void selectStep(Step step) {
        recipeDetailViewModel.validateSelectedStep(step);
    }

    @Override
    public void startRecipeStep(int stepId, List<Step> steps) {
        onRecipeDetailFragmentListener.startRecipeStep(stepId, steps);
    }

    //endregion

    //region Public Methods

    public static RecipeDetailFragment newInstance(Bundle bundle){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion

    //region Inner Classes & Callbacks

    public interface OnRecipeDetailFragmentListener {
        void finishActivity();
        void startRecipeStep(int stepId, List<Step> stepList);
    }

    //endregion
}