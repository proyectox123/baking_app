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

import java.util.Objects;

import static com.mho.bakingapp.utils.Constants.EXTRA_PLAY_WHEN_READY;
import static com.mho.bakingapp.utils.Constants.EXTRA_VIDEO_POSITION;

public class RecipeStepPageFragment extends BaseFragment<FragmentRecipeStepPageBinding, RecipeStepPageViewModel>
        implements RecipeStepPageNavigator {

    //region Constants

    private final static String TAG = RecipeStepPageFragment.class.getSimpleName();

    //endregion

    //region Fields

    private boolean isPlayWhenReady = true;

    private long currentVideoPosition = 0;

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
        recipeStepPageViewModel.initTwoPaneVariable(getContext());

        if(savedInstanceState == null){
            currentVideoPosition = 0;
            isPlayWhenReady = true;
        }else{
            currentVideoPosition = savedInstanceState.getLong(EXTRA_VIDEO_POSITION, 0);
            isPlayWhenReady = savedInstanceState.getBoolean(EXTRA_PLAY_WHEN_READY, true);
        }

        Log.d(TAG, "Superlog onCreate currentVideoPosition " + currentVideoPosition);
        Log.d(TAG, "Superlog onCreate isPlayWhenReady " + isPlayWhenReady);
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
        recipeStepPageViewModel.initRecipeStep(Objects.requireNonNull(getContext()), currentVideoPosition, isPlayWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();

        recipeStepPageViewModel.releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXTRA_VIDEO_POSITION, currentVideoPosition);
        outState.putBoolean(EXTRA_PLAY_WHEN_READY, isPlayWhenReady);
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

    @Override
    public void updateCurrentVideoPosition(long currentPosition) {
        currentVideoPosition = currentPosition;
    }

    @Override
    public void updateIsPlayWhenReady(boolean playWhenReady) {
        isPlayWhenReady = playWhenReady;
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