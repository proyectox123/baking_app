package com.mho.bakingapp.features.recipesteppage;

import com.google.android.exoplayer2.SimpleExoPlayer;

public interface RecipeStepPageNavigator {
    void hidePlayer();
    void setPlayer(SimpleExoPlayer player);
    void showThumbnailRecipe(String thumbnailURL);
    void expandVideoView();
    void hideDescriptionCard();
    void setFullScreenMode();
}