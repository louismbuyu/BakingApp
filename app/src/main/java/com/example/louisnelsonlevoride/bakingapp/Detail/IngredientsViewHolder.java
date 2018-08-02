package com.example.louisnelsonlevoride.bakingapp.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.R;

public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView ingredientsTitle;
    TextView ingredientsInfo;
    Recipe recipe;
    Context context;

    IngredientsOnClickListener ingredientsOnClickListener;

    public IngredientsViewHolder(View itemView, Recipe recipe, Context context, IngredientsOnClickListener ingredientsOnClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.recipe = recipe;
        this.context = context;
        this.ingredientsOnClickListener = ingredientsOnClickListener;
        ingredientsTitle = itemView.findViewById(R.id.ingredients_title);
        ingredientsInfo = itemView.findViewById(R.id.ingredients_textview);

    }

    @Override
    public void onClick(View view) {
        ingredientsOnClickListener.onIngredientsClick();
    }
}
