package com.mho.bakingapp.features.recipestepvideo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mho.bakingapp.R;

public class RecipeStepVideo implements Player.EventListener{

    //region Constants

    private final static String TAG = RecipeStepVideo.class.getSimpleName();

    //endregion

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

    public void initializePlayer(@NonNull Context context, Uri mediaUri, long currentVideoPosition, boolean isPlayWhenReady) {
        if (player != null) {
            return;
        }

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        onRecipeStepVideoListener.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getString(R.string.app_name)), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
        player.prepare(videoSource);

        if (currentVideoPosition != 0){
            player.seekTo(currentVideoPosition);
        }

        player.setPlayWhenReady(isPlayWhenReady);

        /*
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            onRecipeStepVideoListener.setPlayer(player);
        }

        buildMediaSource(context, mediaUri, currentVideoPosition);
        */
    }

    public void setPlayWhenReady(long currentVideoPosition, boolean isPlayWhenReady) {
        Log.d(TAG, "Superlog setPlayWhenReady currentVideoPosition " + currentVideoPosition);
        Log.d(TAG, "Superlog setPlayWhenReady isPlayWhenReady " + isPlayWhenReady);
        Log.d(TAG, "Superlog setPlayWhenReady player == null " + (player == null));
        if(player == null){
            return;
        }

        try {
            player.seekTo(currentVideoPosition);
            player.setPlayWhenReady(isPlayWhenReady);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void releasePlayer() {
        if (player == null) {
            onRecipeStepVideoListener.updateCurrentVideoPosition(C.TIME_UNSET);
            onRecipeStepVideoListener.updateIsPlayWhenReady(false);
            return;
        }

        onRecipeStepVideoListener.updateCurrentVideoPosition(player.getCurrentPosition());
        onRecipeStepVideoListener.updateIsPlayWhenReady(player.getPlayWhenReady());

        player.stop();
        player.release();
        player = null;
    }

    public void stopPlayer(){
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
    }

    //endregion

    //region Private Methods

    private void buildMediaSource(@NonNull Context context, Uri mediaUri, long currentVideoPosition) {
        if(player == null){
            return;
        }

        MediaSource videoSource = createMediaSource(context, mediaUri);

        if(currentVideoPosition != C.TIME_UNSET){
            player.seekTo(currentVideoPosition);
        }

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

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
        void updateCurrentVideoPosition(long currentPosition);
        void updateIsPlayWhenReady(boolean playWhenReady);
    }

    //endregon
}