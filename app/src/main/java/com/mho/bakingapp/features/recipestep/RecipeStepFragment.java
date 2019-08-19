package com.mho.bakingapp.features.recipestep;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.databinding.FragmentRecipeStepBinding;

public class RecipeStepFragment extends BaseFragment<FragmentRecipeStepBinding, RecipeStepViewModel>
        implements RecipeStepNavigator {

    //region Fields

    private RecipeStepViewModel recipeStepViewModel;

    //endregion

    //region Constructor

    public RecipeStepFragment() { }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public int getIdLayout() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RecipeStepViewModel getViewModel() {
        recipeStepViewModel = ViewModelProviders.of(this).get(RecipeStepViewModel.class);
        return recipeStepViewModel;
    }

    @Override
    public void setNavigator() {
        recipeStepViewModel.setNavigator(this);
    }

    //endregion

    //region Public Methods

    public static RecipeStepFragment newInstance(Bundle bundle){
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion
}
