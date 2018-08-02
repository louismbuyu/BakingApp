package com.example.louisnelsonlevoride.bakingapp.Recipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louisnelsonlevoride.bakingapp.Detail.DetailRecipeActivity;
import com.example.louisnelsonlevoride.bakingapp.DialogInterface;
import com.example.louisnelsonlevoride.bakingapp.Models.Ingredient;
import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.R;

import java.util.List;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView recipeImageView;
    ImageView recipeFavouriteImageView;
    TextView recipeTitleTextView;
    List<Recipe> recipes;
    Context context;
    SharedPreferences sharedpreferences;
    RecipeAdapter recipeAdapter;
    DialogInterface dialogInterface;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String FAVOURITE = "FAVOURITE";
    public static final String FAVOURITE_NAME = "FAVOURITE_NAME";
    private String TAG = "RecipeViewHolder";

    public RecipeViewHolder(View itemView, List<Recipe> recipes, Context context, RecipeAdapter recipeAdapter, DialogInterface dialogInterface) {
        super(itemView);
        itemView.setOnClickListener(this);
        recipeImageView = itemView.findViewById(R.id.recipeImage);
        recipeFavouriteImageView = itemView.findViewById(R.id.recipeFavouriteImage);
        recipeTitleTextView = itemView.findViewById(R.id.recipeTitle);
        this.recipes = recipes;
        this.context = context;
        this.recipeAdapter = recipeAdapter;
        this.dialogInterface = dialogInterface;
        sharedpreferences = this.context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        recipeFavouriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFavourite();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        Recipe item = recipes.get(position);
        Intent intent = new Intent(context, DetailRecipeActivity.class);
        intent.putExtra("recipe_item", item);
        context.startActivity(intent);
    }

    public void saveFavourite(){
        int position = getAdapterPosition();
        Recipe item = recipes.get(position);
        List<Ingredient> ingredients = item.getIngredients();
        String ingredientWidgetInfo = item.getName() + ": ";
        for (int i = 0; i < ingredients.size(); i++){
            if (i == ingredients.size() - 1){
                ingredientWidgetInfo += ingredients.get(i).getIngredient() + ".";
            }else{
                ingredientWidgetInfo += ingredients.get(i).getIngredient() + ", ";
            }
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        //editor.commit(); // commit changes
        editor.putString(FAVOURITE, ingredientWidgetInfo);
        editor.putString(FAVOURITE_NAME, item.getName());
        editor.commit();
        dialogInterface.showDialog(item.getName());
    }
}
