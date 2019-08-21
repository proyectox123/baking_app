package com.mho.bakingapp.features.recipestepvideo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

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

import static com.mho.bakingapp.utils.Constants.EXTRA_PLAY_WHEN_READY;
import static com.mho.bakingapp.utils.Constants.EXTRA_VIDEO_POSITION;

public class RecipeStepVideo implements Player.EventListener{

    //region Constants

    private final static String TAG = RecipeStepVideo.class.getSimpleName();

    //endregion

    //region Fields

    private boolean isPlayWhenReady = true;
    private long currentVideoPosition = 0;

    private Context context;
    private SimpleExoPlayer player;

    private OnRecipeStepVideoListener onRecipeStepVideoListener;

    //endregion

    //region Constructors

    public RecipeStepVideo(Context context, OnRecipeStepVideoListener onRecipeStepVideoListener){
        this.context = context;
        this.onRecipeStepVideoListener = onRecipeStepVideoListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                break;
            case Player.STATE_ENDED:
                break;
            case Player.STATE_IDLE:
                break;
            case Player.STATE_READY:
                break;
            default:
                break;
        }
    }

    //endregion

    //region Public Methods

    public void validateRecipeStepPageInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            return;
        }

        currentVideoPosition = savedInstanceState.getLong(EXTRA_VIDEO_POSITION, 0);
        isPlayWhenReady = savedInstanceState.getBoolean(EXTRA_PLAY_WHEN_READY, true);
    }

    public void saveInstanceState(Bundle outState) {
        outState.putLong(EXTRA_VIDEO_POSITION, currentVideoPosition);
        outState.putBoolean(EXTRA_PLAY_WHEN_READY, isPlayWhenReady);
    }

    public void initializePlayer(Uri mediaUri) {
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
    }

    public void releasePlayer() {
        if (player == null) {
            currentVideoPosition = 0;
            isPlayWhenReady = false;
            return;
        }

        currentVideoPosition = player.getCurrentPosition();
        isPlayWhenReady = player.getPlayWhenReady();

        player.stop();
        player.release();
        player = null;
    }

    //endregion

    //region Private Methods

    private void buildMediaSource(Uri mediaUri, long currentVideoPosition) {
        if(player == null){
            return;
        }

        MediaSource videoSource = createMediaSource(mediaUri);

        if(currentVideoPosition != C.TIME_UNSET){
            player.seekTo(currentVideoPosition);
        }

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

    private MediaSource createMediaSource(Uri mediaUri){
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                getUserAgent(),
                getDefaultBandwidthMeter()
        );

        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
    }

    private String getUserAgent(){
        return Util.getUserAgent(context, context.getString(R.string.app_name));
    }

    private DefaultBandwidthMeter getDefaultBandwidthMeter(){
        return new DefaultBandwidthMeter.Builder(context).build();
    }

    //endregion

    //region Inner Classes & Callbacks

    public interface OnRecipeStepVideoListener {
        void setPlayer(SimpleExoPlayer player);
    }

    //endregon
}