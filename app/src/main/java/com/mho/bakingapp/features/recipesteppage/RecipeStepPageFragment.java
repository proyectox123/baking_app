package com.mho.bakingapp.features.recipesteppage;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.databinding.FragmentRecipeStepPageBinding;

public class RecipeStepPageFragment extends BaseFragment<FragmentRecipeStepPageBinding, RecipeStepPageViewModel>
        implements RecipeStepPageNavigator {

    //region Constructors

    public RecipeStepPageFragment() { }

    //endregion

    //region Private Methods

    private RecipeStepPageViewModel recipeStepPageViewModel;

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeStepPageViewModel.validateRecipeStepPageArguments(getArguments());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeStepPageViewModel.initRecipeStep();
    }

    @Override
    public int getIdLayout() {
        return R.layout.fragment_recipe_step_page;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RecipeStepPageViewModel getViewModel() {
        recipeStepPageViewModel = ViewModelProviders.of(this).get(RecipeStepPageViewModel.class);
        return recipeStepPageViewModel;
    }

    @Override
    public void setNavigator() {
        recipeStepPageViewModel.setNavigator(this);
    }

    //endregion

    //region Public Methods

    public static RecipeStepPageFragment newInstance(Bundle bundle){
        RecipeStepPageFragment fragment = new RecipeStepPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion
}