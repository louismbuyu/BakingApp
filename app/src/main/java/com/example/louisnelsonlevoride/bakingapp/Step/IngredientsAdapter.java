package com.example.louisnelsonlevoride.bakingapp.Step;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louisnelsonlevoride.bakingapp.Models.Ingredient;
import com.example.louisnelsonlevoride.bakingapp.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsRecycleViewHolder> {

    private Context context;
    private List<Ingredient> ingredients;
    private String TAG = "DetailIngredientsAdapter";

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_ingredient_item,parent,false);
        return new IngredientsRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecycleViewHolder holder, int position) {
        holder.nameTextView.setText(ingredients.get(position).getIngredient());
        holder.amountTextView.setText(""+ingredients.get(position).getQuantity());
        holder.measureTextView.setText(ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
