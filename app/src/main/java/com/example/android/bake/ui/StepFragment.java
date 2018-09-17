package com.example.android.bake.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bake.R;
import com.example.android.bake.helper.AndroidExtensions;
import com.example.android.bake.model.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {

    private static final String ARG_STEP = "argStep";

    private Step mStep;

    private SimpleExoPlayer player;
    PlayerView playerView;

    public interface StepFragmentListener {
        void onConfigurationChanged(Configuration newConfig);
    }

    StepFragmentListener listener;

    public StepFragment() {}

    public static StepFragment newInstance(Step step) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getParcelable(ARG_STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.playerView);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView textLoading = view.findViewById(R.id.tv_loading);
        TextView textStepDetail = view.findViewById(R.id.tv_step_details);
        textStepDetail.setText(mStep.getDescription());

        if (!mStep.getVideoURL().equals("")) {
            new Handler().post(() -> {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
                DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

                playerView.setPlayer(player);

                if (getContext() != null) {
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getContext().getPackageName()));
                    MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));
                    player.prepare(videoSource);

                    player.addListener(new Player.EventListener() {
                        @Override
                        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {}

                        @Override
                        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

                        @Override
                        public void onLoadingChanged(boolean isLoading) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                if (isLoading) {
                                    playerView.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    textLoading.setVisibility(View.VISIBLE);
                                } else {
                                    playerView.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    textLoading.setVisibility(View.GONE);
                                }
                            });
                        }

                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {}

                        @Override
                        public void onRepeatModeChanged(int repeatMode) {}

                        @Override
                        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                        }

                        @Override
                        public void onPlayerError(ExoPlaybackException error) {}

                        @Override
                        public void onPositionDiscontinuity(int reason) {}

                        @Override
                        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}

                        @Override
                        public void onSeekProcessed() {}
                    });
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            textLoading.setVisibility(View.GONE);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !AndroidExtensions.getIsTablet(getActivity())) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) playerView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.bottomMargin = 0;
                params.leftMargin = 0;
                params.rightMargin = 0;
                params.topMargin = 0;
                playerView.setLayoutParams(params);
                hideSystemUI();
            }
        }

    }

    private void hideSystemUI() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.release();
        }
    }
}
