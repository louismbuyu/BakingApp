package com.example.louisnelsonlevoride.bakingapp.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;

public class DetailRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView textViewTitle;
    TextView textViewDescription;
    TextView textViewFormat;
    ImageView imageView;
    Recipe recipe;
    Context context;

    DetailRecipeOnClickListener detailRecipeOnClickListener;

    public DetailRecipeViewHolder(View itemView, Recipe recipe, Context context, DetailRecipeOnClickListener detailRecipeOnClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.recipe = recipe;
        this.context = context;
        this.detailRecipeOnClickListener = detailRecipeOnClickListener;
        textViewTitle = itemView.findViewById(R.id.title_id);
        textViewDescription = itemView.findViewById(R.id.description_id);
        textViewFormat = itemView.findViewById(R.id.format_tv_id);
        imageView = itemView.findViewById(R.id.format_img_id);
    }

    @Override
    public void onClick(View view) {
        Step step = recipe.getSteps().get(getAdapterPosition() - 1);
        detailRecipeOnClickListener.onStepClick(getAdapterPosition(),step);
    }
}
