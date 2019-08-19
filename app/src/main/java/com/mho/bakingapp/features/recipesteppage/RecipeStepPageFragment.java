package com.mho.bakingapp.features.recipesteppage;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.databinding.FragmentRecipeStepPageBinding;

public class RecipeStepPageFragment extends BaseFragment<FragmentRecipeStepPageBinding, RecipeStepPageViewModel>
        implements RecipeStepPageNavigator {

    //region Constructors

    public RecipeStepPageFragment() { }

    //endregion

    //region Fields

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
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeStepPageViewModel.initRecipeStep(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        recipeStepPageViewModel.releasePlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUiFullScreen();
        } else {
            hideSystemUi();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUiFullScreen() {
        binding.videoRecipeStep.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        binding.videoRecipeStep.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
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

    @Override
    public void hidePlayer() {
        binding.videoRecipeStep.setVisibility(View.GONE);
    }

    @Override
    public void setPlayer(SimpleExoPlayer player) {
        binding.videoRecipeStep.setVisibility(View.VISIBLE);
        binding.videoRecipeStep.setPlayer(player);
    }

    //endregion

    //region Private Methods

    static RecipeStepPageFragment newInstance(Bundle bundle){
        RecipeStepPageFragment fragment = new RecipeStepPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion
}