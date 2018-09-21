package com.example.android.bake.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bake.R;
import com.example.android.bake.helper.AndroidExtensions;
import com.example.android.bake.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {

    private static final String ARG_STEP = "argStep";
    private static final String PLAYER_POSITION = "playerPosition";
    private static final String PLAYER_PLAY_WHEN_READY = "playerPlayWhenReady";

    private Step mStep;

    private SimpleExoPlayer player;
    private PlayerView playerView;

    private Bundle savedInstanceState;


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.playerView);
        TextView textStepDetail = view.findViewById(R.id.tv_step_details);
        textStepDetail.setText(mStep.getDescription());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changePlayerViewToFullScreenMode();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            initPlayer();
        }
    }

    private void changePlayerViewToFullScreenMode(){
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong(PLAYER_POSITION, player.getCurrentPosition());
            outState.putBoolean(PLAYER_PLAY_WHEN_READY, player.getPlayWhenReady());
        }
    }

    private void initPlayer() {
        if (getContext() != null) {
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

                        if (savedInstanceState != null) {
                            player.seekTo(savedInstanceState.getLong(PLAYER_POSITION, 0));
                            player.setPlayWhenReady(savedInstanceState.getBoolean(PLAYER_PLAY_WHEN_READY, false));
                        }

                        new Handler(Looper.getMainLooper()).post(() -> playerView.setVisibility(View.VISIBLE));
                    }
                });
            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            this.savedInstanceState = savedInstanceState;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            if (player != null) {
                player.release();
                player = null;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (player != null) {
                player.release();
                player = null;
            }
        }
    }
}
