package com.example.louisnelsonlevoride.bakingapp.Step;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.louisnelsonlevoride.bakingapp.R;

public class IngredientsRecycleViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView amountTextView;
    TextView measureTextView;

    public IngredientsRecycleViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.ingredient_name);
        amountTextView = itemView.findViewById(R.id.ingredient_amount);
        measureTextView = itemView.findViewById(R.id.ingredient_measure);
    }
}
