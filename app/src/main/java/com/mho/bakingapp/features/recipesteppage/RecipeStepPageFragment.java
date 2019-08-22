package com.mho.bakingapp.features.recipesteppage;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.databinding.FragmentRecipeStepPageBinding;
import com.squareup.picasso.Picasso;

public class RecipeStepPageFragment extends BaseFragment<FragmentRecipeStepPageBinding, RecipeStepPageViewModel>
        implements RecipeStepPageNavigator {

    //region Constants

    private final static String TAG = RecipeStepPageFragment.class.getSimpleName();

    //endregion

    //region Fields

    private RecipeStepPageViewModel recipeStepPageViewModel;

    //endregion

    //region Constructors

    public RecipeStepPageFragment() { }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        recipeStepPageViewModel.validateRecipeStepPageArguments(getArguments());
        recipeStepPageViewModel.validateRecipeStepPageInstanceState(savedInstanceState);
        recipeStepPageViewModel.initTwoPaneVariable(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");

        recipeStepPageViewModel.validateRecipeStepPageOrientation(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeStepPageViewModel.initPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();

        recipeStepPageViewModel.releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        recipeStepPageViewModel.saveInstanceState(outState);
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
        binding.imageRecipeStepThumbnail.setVisibility(View.GONE);
        binding.videoRecipeStep.setVisibility(View.VISIBLE);
        binding.videoRecipeStep.setPlayer(player);
    }

    @Override
    public void showThumbnailRecipe(String thumbnailURL) {
        binding.videoRecipeStep.setVisibility(View.GONE);
        binding.imageRecipeStepThumbnail.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(thumbnailURL)
                .placeholder(R.drawable.ic_chef)
                .error(R.drawable.ic_chef)
                .into(binding.imageRecipeStepThumbnail);
    }

    @Override
    public void expandVideoView() {
        binding.videoRecipeStep.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.videoRecipeStep.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void hideDescriptionCard() {
        binding.cardRecipeStepDescription.setVisibility(View.GONE);
    }

    @Override
    public void setFullScreenMode() {
        Activity activity = getActivity();
        if(activity != null){
            Window window = activity.getWindow();
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                visibility = visibility | View.SYSTEM_UI_FLAG_IMMERSIVE;
            }

            window.getDecorView().setSystemUiVisibility(visibility);
        }
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