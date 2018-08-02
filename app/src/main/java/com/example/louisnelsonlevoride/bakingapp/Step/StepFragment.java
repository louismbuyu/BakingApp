package com.example.louisnelsonlevoride.bakingapp.Step;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.concurrent.ExecutionException;

public class StepFragment extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private Context context;
    private Step step;
    private Recipe recipe;
    private int position;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button prevButton;
    private Button nextButton;
    private DisplayMetrics outMetrics;

    private final static String TAG = "DetailStepFragment";

    public StepFragment() {
    }

    @SuppressLint("ValidFragment")
    public StepFragment(Context context, Step step, Recipe recipe, int position, DisplayMetrics outMetrics) {
        this.context = context;
        this.step = step;
        this.recipe = recipe;
        this.position = position;
        this.outMetrics = outMetrics;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);

        simpleExoPlayerView = rootView.findViewById(R.id.video_view);
        titleTextView = rootView.findViewById(R.id.detail_step_title_id);
        descriptionTextView = rootView.findViewById(R.id.detail_step_description_id);
        prevButton = rootView.findViewById(R.id.prevBtn);
        nextButton = rootView.findViewById(R.id.nextBtn);
        getActivity().setTitle("Step "+step.getId());
        SetupViews();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null){
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void SetupViews(){
        descriptionTextView.setText(step.getDescription());
        if (step.getId() == 0){
            titleTextView.setText(step.getShortDescription());
        }else{
            titleTextView.setText("Step "+step.getId()+": "+step.getShortDescription());
        }

        if (step.getVideoURL().isEmpty()) {
            simpleExoPlayerView.getLayoutParams().height = 0;
        }else {
            int width1 = outMetrics.widthPixels/3;
            int width2 = width1*2;
            int finalHeight = width2*(9/16);
            if (finalHeight != 0){
                simpleExoPlayerView.getLayoutParams().height = finalHeight;
            }
            playVideo();
        }

        if (step.getId() == 0){
            prevButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }else if (step.getId() == recipe.getSteps().size() - 1){
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        }else{
            prevButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        }
        SetupButtons();
    }

    private void SetupButtons(){
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step prevStep = recipe.getSteps().get(step.getId() - 1);
                step = prevStep;
                SetupViews();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step nextStep = recipe.getSteps().get(step.getId() + 1);
                step = nextStep;
                SetupViews();
            }
        });
    }

    private void playVideo(){
        try {
            if (!step.getThumbnailURL().isEmpty()){
                if (!step.getThumbnailURL().endsWith(".mp4")){
                    Bitmap bitmap = Glide.with(this).asBitmap().load(step.getThumbnailURL()).into(100,100).get();
                    Bitmap bitmap1 = Glide.with(this).asBitmap().load(step.getThumbnailURL()).submit().get();
                    simpleExoPlayerView.setDefaultArtwork(bitmap1);
                }
            }

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(); //new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector,loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            Uri uri = Uri.parse(step.getVideoURL());
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(context,"exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(uri,defaultDataSourceFactory,extractorsFactory,null,null);

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            Log.e(TAG, "Error: "+e.toString());
        }
    }

    private void releasePlayer(){
        if (simpleExoPlayer != null){
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
