package com.example.louisnelsonlevoride.bakingapp.Step;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.R;

@SuppressLint("ValidFragment")
public class IngredientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private Recipe recipe;
    private IngredientsAdapter ingredientsAdapter;
    private Context context;

    public IngredientsFragment(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_ingredients, container, false);
        getActivity().setTitle("Ingredients");
        recyclerView = rootView.findViewById(R.id.ingredients_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        ingredientsAdapter = new IngredientsAdapter(context, recipe.getIngredients());
        recyclerView.setAdapter(ingredientsAdapter);

        return rootView;
    }
}
