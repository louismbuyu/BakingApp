package com.example.louisnelsonlevoride.bakingapp.Detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;

import java.util.List;

public class RecipeFragment extends Fragment {

    List<Step> steps;
    Recipe recipe;
    RecyclerView recyclerView;
    DetailRecipeAdapter detailRecipeAdapter;
    private final static String TAG = "MainActivity";
    private GridLayoutManager gridLayoutManager;
    Context context;
    DetailRecipeOnClickListener detailRecipeOnClickListener;
    IngredientsOnClickListener ingredientsOnClickListener;

    public RecipeFragment() {
    }

    @SuppressLint("ValidFragment")
    public RecipeFragment(Recipe recipe, Context context, DetailRecipeOnClickListener detailRecipeOnClickListener, IngredientsOnClickListener ingredientsOnClickListener) {
        this.recipe = recipe;
        this.context = context;
        this.detailRecipeOnClickListener = detailRecipeOnClickListener;
        this.ingredientsOnClickListener = ingredientsOnClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.recipe_fragment_id);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        detailRecipeAdapter = new DetailRecipeAdapter(inflater.getContext(), recipe, detailRecipeOnClickListener, ingredientsOnClickListener);
        recyclerView.setAdapter(detailRecipeAdapter);

        return rootView;
    }
}
