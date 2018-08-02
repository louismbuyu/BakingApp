package com.example.louisnelsonlevoride.bakingapp.Detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louisnelsonlevoride.bakingapp.Models.Ingredient;
import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;

import java.util.List;

public class DetailRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Recipe recipe;
    List<Ingredient> ingredients;
    Context context;
    private final static String TAG = "DetailRecipeAdapter";
    DetailRecipeOnClickListener detailRecipeOnClickListener;
    IngredientsOnClickListener ingredientsOnClickListener;

    public DetailRecipeAdapter(Context context, Recipe recipe, DetailRecipeOnClickListener detailRecipeOnClickListener, IngredientsOnClickListener ingredientsOnClickListener) {
        this.recipe = recipe;
        this.context = context;
        this.detailRecipeOnClickListener = detailRecipeOnClickListener;
        this.ingredientsOnClickListener = ingredientsOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycle_item,parent,false);
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_recycle_item,parent,false);
        if (viewType == 0)
            return new IngredientsViewHolder(view1, recipe, context, ingredientsOnClickListener);
        else
            return new DetailRecipeViewHolder(view, recipe, context, detailRecipeOnClickListener);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }else {
            return 1;
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == 0){
            IngredientsViewHolder detailIngredientsViewHolder = (IngredientsViewHolder) holder;
            detailIngredientsViewHolder.ingredientsTitle.setText("Ingredients");
            List<Ingredient> ingredients = recipe.getIngredients();
            String ingredientsText = "";
            for (int i = 0; i < ingredients.size(); i++){
                String item = ingredients.get(i).getIngredient();
                Double quantity = ingredients.get(i).getQuantity();
                String measure = ingredients.get(i).getMeasure();
                ingredientsText += item+": "+quantity+" X "+measure+"\n\n";
            }
            detailIngredientsViewHolder.ingredientsInfo.setText(ingredientsText);
        }else{
            DetailRecipeViewHolder detailRecipeViewHolder = (DetailRecipeViewHolder) holder;
            Step step = recipe.getSteps().get(position - 1);

            if (step.getId() == 0){
                detailRecipeViewHolder.textViewTitle.setText(step.getShortDescription());
            }else{
                detailRecipeViewHolder.textViewTitle.setText("Step "+step.getId()+": "+step.getShortDescription());
            }

            detailRecipeViewHolder.textViewDescription.setText(step.getDescription());
            if (step.getVideoURL().isEmpty()){
                detailRecipeViewHolder.textViewFormat.setText("Text format");
                detailRecipeViewHolder.imageView.setBackgroundResource(R.drawable.text);
            }else{
                detailRecipeViewHolder.textViewFormat.setText("Video format");
                detailRecipeViewHolder.imageView.setBackgroundResource(R.drawable.video);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (recipe != null){
            return recipe.getSteps().size() + 1;
        }else{
            return  0;
        }

    }
}
