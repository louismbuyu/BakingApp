package com.example.louisnelsonlevoride.bakingapp.Step;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.DisplayMetrics;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.google.android.exoplayer2.PlaybackParameters;

public class StepRotationViewModel extends ViewModel {
    public Long position;
    public int playbackState;
    public PlaybackParameters playbackParameters;

    public Context context;
    public Step step;
    public Recipe recipe;
    public DisplayMetrics outMetrics;
}
