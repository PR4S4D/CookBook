package com.slp.cookbook.ui;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Steps;
import com.slp.cookbook.utils.CookBookConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment implements CookBookConstants, Player.EventListener {

    @Bind(R.id.video_player)
    SimpleExoPlayerView videoPlayerView;
    @Bind(R.id.next_step)
    Button nextStep;
    @Bind(R.id.prev_step)
    Button prevStep;
    @Bind(R.id.step_description)
    TextView stepDescription;
    @Bind(R.id.recipe_step_image)
    ImageView recipeStepImage;

    private Steps step;
    private List<Steps> steps;
    private int position;
    private MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder stateBuilder;

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private SimpleExoPlayer player;


    public StepDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        if (null != savedInstanceState) {

            steps = savedInstanceState.getParcelableArrayList(STEPS);
            position = savedInstanceState.getInt(POSITION);
        }

        initializeViews();
        setNavigationButtonClickListener();

        return rootView;
    }

    private void setNavigationButtonClickListener() {
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != (steps.size() - 1)) {
                    position++;
                    initializeViews();
                }
            }
        });

        prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 0) {
                    position--;
                    initializeViews();
                }
            }
        });
    }

    private void initializeViews() {

        prevStep.setClickable(true);
        nextStep.setClickable(true);
        if (position == 0) {
            prevStep.setClickable(false);
        } else if (position == (steps.size() - 1)) {
            nextStep.setClickable(false);
        }
        step = steps.get(position);
        stepDescription.setText(step.getDescription());
        if (null != player) {
            releasePlayer();
        }
        if (!TextUtils.isEmpty(step.getVideoURL())) {

            initiateVideoPlayer(Uri.parse(step.getVideoURL()));
        }

        if(TextUtils.isEmpty(step.getThumbnailURL())){
            Picasso.with(getActivity()).load(R.drawable.cookbook).into(recipeStepImage);
        }else{
            Picasso.with(getActivity()).load(step.getThumbnailURL()).error(R.drawable.cookbook).into(recipeStepImage);

        }
    }

    // ref: https://google.github.io/ExoPlayer/guide.html#preparing-the-player
    private void initiateVideoPlayer(Uri videoUri) {

        Handler mainHandler = new Handler();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        if (null != player) {
            releasePlayer();
        }
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        videoPlayerView.setPlayer(player);


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), COOKBOOK), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        initialeMediaSession();

    }

    private void initialeMediaSession() {
        player.addListener(this);
        mediaSessionCompat = new MediaSessionCompat(getActivity(), COOKBOOK);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(stateBuilder.build());
        mediaSessionCompat.setCallback(new MediaSessionCallback());
        mediaSessionCompat.setActive(true);

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, player.getCurrentPosition(), 1f);
        } else if (playbackState == Player.STATE_READY) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, player.getCurrentPosition(), 1f);
        }
        mediaSessionCompat.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    public class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
        }

        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }
    }

    public void setStep(Steps step) {
        this.step = step;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (null != player) {
            player.stop();
            player.release();
            player = null;
            videoPlayerView.setPlayer(null);
            mediaSessionCompat.setActive(false);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS, (ArrayList<? extends Parcelable>) steps);
        outState.putInt(POSITION, position);
    }


}
