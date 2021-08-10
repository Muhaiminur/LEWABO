package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.lewabo.lewabo.databinding.FragmentPlayerPageBinding;
import com.lewabo.lewabo.utility.Utility;

public class PlayerPage extends Fragment implements PlaybackPreparer, PlayerControlView.VisibilityListener, Player.Listener {
    Utility utility;
    Context context;
    FragmentPlayerPageBinding binding;
    String videoUri = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_20mb.mp4";
    SimpleExoPlayer player;
    Handler mHandler;
    Runnable mRunnable;
    int HI_BITRATE = 2097152;
    int MI_BITRATE = 1048576;
    int LO_BITRATE = 524288;
    //DefaultTrackSelector trackSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentPlayerPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
                setUp();
                //updateButtonVisibilities();
                Log.d("visiblity", "check = ");
                binding.videoFullScreenPlayer.setControllerVisibilityListener(this);
                binding.videoFullScreenPlayer.requestFocus();
                binding.videoFullScreenPlayer.setShutterBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    private void setUp() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(Uri.parse(videoUri));
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            /*LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    2 * VideoPlayerConfig.MIN_BUFFER_DURATION,
                    2 * VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);*/

            /*BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);*/
            // 2. Create the player
            player = new SimpleExoPlayer.Builder(context).build();
            binding.videoFullScreenPlayer.setPlayer(player);
        }
    }

    private void buildMediaSource(Uri mUri) {
        /*// Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, getString(R.string.app_name)), bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        MediaSource videoSource2 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"));
        ConcatenatingMediaSource concatenatedSource =
                new ConcatenatingMediaSource(videoSource, videoSource2);*/
        // Prepare the player with the source.

        MediaItem mediaItem = MediaItem.fromUri(mUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    private void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }



    /*@Override
    public void onRestart() {
        super.onRestart();
        resumePlayer();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlaybackStateChanged(@Player.State int playbackState) {
        switch (playbackState) {

            case Player.STATE_BUFFERING:
                binding.spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                binding.spinnerVideoDetails.setVisibility(View.GONE);
                player.play();
                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onVisibilityChange(int visibility) {
        Log.d("visiblity", "check = " + visibility);
        binding.videoQuality.setVisibility(visibility);


    }

    @Override
    public void preparePlayback() {
        initializePlayer();
    }

    /*private void updateButtonVisibilities() {
        if (player == null) {
            Log.d("OK", "ONE");
            return;
        }

        //MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            Log.d("OK", "TWO");
            return;
        }

        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                int label;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.exo_track_selection_title_audio;
                        Log.d("OK", label + "audio");
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.exo_track_selection_title_video;
                        Log.d("OK", label + "video");
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.exo_track_selection_title_text;
                        Log.d("OK", label + "text");
                        break;
                    default:
                        continue;
                }
                Log.d("OK", label + "ONE");
                binding.videoQuality.setText(label);
                binding.videoQuality.setTag(i);
            }
        }
    }*/





}