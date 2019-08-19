package com.mho.bakingapp.features.recipestepvideo;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mho.bakingapp.R;

public class RecipeStepVideo implements Player.EventListener{

    //region Fields

    private SimpleExoPlayer player;

    private OnRecipeStepVideoListener onRecipeStepVideoListener;

    //endregion

    //region Constructors

    public RecipeStepVideo(OnRecipeStepVideoListener onRecipeStepVideoListener){
        this.onRecipeStepVideoListener = onRecipeStepVideoListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                //spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                //spinnerVideoDetails.setVisibility(View.GONE);

                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }

    //endregion

    //region Public Methods

    public void initializePlayer(@NonNull Context context) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            onRecipeStepVideoListener.setPlayer(player);
        }
    }

    public void buildMediaSource(@NonNull Context context, Uri mediaUri) {
        MediaSource videoSource = createMediaSource(context, mediaUri);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

    public void releasePlayer() {
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
    }

    //endregion

    //region Private Methods

    private MediaSource createMediaSource(@NonNull Context context, Uri mediaUri){
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                getUserAgent(context),
                getDefaultBandwidthMeter(context)
        );

        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
    }

    private String getUserAgent(Context context){
        return Util.getUserAgent(context, context.getString(R.string.app_name));
    }

    private DefaultBandwidthMeter getDefaultBandwidthMeter(Context context){
        return new DefaultBandwidthMeter.Builder(context).build();
    }

    //endregion

    //region Inner Classes & Callbacks

    public interface OnRecipeStepVideoListener {
        void setPlayer(SimpleExoPlayer player);
    }

    //endregon
}