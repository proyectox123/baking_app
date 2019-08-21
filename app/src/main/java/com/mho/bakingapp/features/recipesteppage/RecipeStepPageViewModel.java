package com.mho.bakingapp.features.recipesteppage;

import android.app.Application;
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

import static com.mho.bakingapp.utils.Constants.EXTRA_PLAY_WHEN_READY;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP;
import static com.mho.bakingapp.utils.Constants.EXTRA_VIDEO_POSITION;

public class RecipeStepPageViewModel extends BaseViewModel<RecipeStepPageNavigator> implements
        RecipeStepVideo.OnRecipeStepVideoListener {

    //region Fields

    public final MutableLiveData<String> recipeStepDescription = new MutableLiveData<>();

    private boolean isTwoPane;
    private Step step;
    private RecipeStepVideo recipeStepVideo;

    //endregion

    //region Constructors

    public RecipeStepPageViewModel(@NonNull Application application) {
        super(application);

        this.recipeStepVideo = new RecipeStepVideo(getApplication(), this);
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void setPlayer(SimpleExoPlayer player) {
        getNavigator().setPlayer(player);
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

    void validateRecipeStepPageInstanceState(Bundle savedInstanceState){
        recipeStepVideo.validateRecipeStepPageInstanceState(savedInstanceState);
    }

    void saveInstanceState(Bundle outState){
        recipeStepVideo.saveInstanceState(outState);
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

    void initPlayer(){
        recipeStepDescription.setValue(step.getDescription());
        validateRecipeStepVideo(step);
    }

    void releasePlayer(){
        recipeStepVideo.releasePlayer();
    }

    private void validateRecipeStepVideo(Step step){
        if (step.getVideoURL() == null || step.getVideoURL().trim().isEmpty()) {
            getNavigator().hidePlayer();
            return;
        }

        recipeStepVideo.initializePlayer(Uri.parse(step.getVideoURL()));
    }

    //endregion
}