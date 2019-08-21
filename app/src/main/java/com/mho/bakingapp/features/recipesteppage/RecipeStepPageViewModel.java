package com.mho.bakingapp.features.recipesteppage;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.features.recipestepvideo.RecipeStepVideo;

import static com.mho.bakingapp.utils.Constants.EXTRA_STEP;

public class RecipeStepPageViewModel extends BaseViewModel<RecipeStepPageNavigator> implements
        RecipeStepVideo.OnRecipeStepVideoListener {

    //region Fields

    public final MutableLiveData<String> recipeStepDescription = new MutableLiveData<>();

    private boolean isTwoPane;

    private Step step;

    private RecipeStepVideo recipeStepVideo;

    //endregion

    //region Constructors

    public RecipeStepPageViewModel(){
        super();

        this.recipeStepVideo = new RecipeStepVideo(this);
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void setPlayer(SimpleExoPlayer player) {
        getNavigator().setPlayer(player);
    }

    @Override
    public void updateCurrentVideoPosition(long currentPosition) {
        getNavigator().updateCurrentVideoPosition(currentPosition);
    }

    @Override
    public void updateIsPlayWhenReady(boolean playWhenReady) {
        getNavigator().updateIsPlayWhenReady(playWhenReady);
    }

    //endregion

    //region Private Methods

    void validateRecipeStepPageArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if(!arguments.containsKey(EXTRA_STEP)){
            return;
        }

        step = arguments.getParcelable(EXTRA_STEP);
    }

    void initTwoPaneVariable(Context context){
        isTwoPane = context.getResources().getBoolean(R.bool.two_pane_mode);
    }

    void validateRecipeStepPageOrientation(Context context){
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            getNavigator().expandVideoView();
            getNavigator().hideDescriptionCard();
            getNavigator().setFullScreenMode();
        }
    }

    void initRecipeStep(@NonNull Context context, long currentVideoPosition, boolean isPlayWhenReady) {
        recipeStepDescription.setValue(step.getDescription());
        validateRecipeStepVideo(context, step, currentVideoPosition, isPlayWhenReady);
    }

    void setPlayWhenReady(long currentVideoPosition, boolean isPlayWhenReady){
        recipeStepVideo.setPlayWhenReady(currentVideoPosition, isPlayWhenReady);
    }

    void releasePlayer(){
        recipeStepVideo.releasePlayer();
    }

    void stopPlayer(){
        recipeStepVideo.stopPlayer();
    }

    private void validateRecipeStepVideo(Context context, Step step, long currentVideoPosition, boolean isPlayWhenReady){
        if (step.getVideoURL() == null || step.getVideoURL().trim().isEmpty()) {
            getNavigator().hidePlayer();
            return;
        }

        recipeStepVideo.initializePlayer(context, Uri.parse(step.getVideoURL()), currentVideoPosition, isPlayWhenReady);
    }

    //endregion
}