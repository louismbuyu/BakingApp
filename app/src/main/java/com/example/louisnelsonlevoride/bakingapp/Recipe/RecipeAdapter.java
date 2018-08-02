package com.example.louisnelsonlevoride.bakingapp.Recipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.louisnelsonlevoride.bakingapp.DialogInterface;
import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.R;

import java.util.List;

import static com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeViewHolder.FAVOURITE_NAME;
import static com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeViewHolder.MyPREFERENCES;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    List<Recipe> recipeList;
    Context context;
    SharedPreferences sharedpreferences;
    String favourite_recipe_name;
    private String TAG = "RecipeAdapter";
    private DialogInterface dialogInterface;

    public RecipeAdapter(Context context, List<Recipe> recipeList,DialogInterface dialogInterface) {
        this.recipeList = recipeList;
        this.context = context;
        this.dialogInterface = dialogInterface;
        sharedpreferences = this.context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        favourite_recipe_name = sharedpreferences.getString(FAVOURITE_NAME, null);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        return new RecipeViewHolder(view, recipeList, context, this, dialogInterface);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public List<Recipe> getRecipes() {
        return recipeList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeTitleTextView.setText(recipeList.get(position).getName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.defaultfood)
                .error(R.drawable.defaultfood);
        String fullImageUrl = recipeList.get(position).getImageUrl();

        if (recipeList.get(position).getName().toString().toLowerCase().equals("nutella pie")){
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.nutellapie)).apply(options).into(holder.recipeImageView);
        }else if (recipeList.get(position).getName().toString().toLowerCase().equals("brownies")){
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.brownies)).apply(options).into(holder.recipeImageView);
        }else if (recipeList.get(position).getName().toString().toLowerCase().equals("cheesecake")){
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.chessecake)).apply(options).into(holder.recipeImageView);
        }else if (recipeList.get(position).getName().toString().toLowerCase().equals("yellow cake")){
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.yellowcake)).apply(options).into(holder.recipeImageView);
        }else{
            Glide.with(context).load(fullImageUrl).apply(options).into(holder.recipeImageView);
        }


        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.unfilledstar)
                .error(R.drawable.unfilledstar);

        if (favourite_recipe_name != null){
            if (favourite_recipe_name.isEmpty() == false){
                if (recipeList.get(position).getName().equals(favourite_recipe_name)){
                    Glide.with(context).load(R.drawable.filledstar).apply(options2).into(holder.recipeFavouriteImageView);
                }else{
                    Glide.with(context).load(R.drawable.unfilledstar).apply(options2).into(holder.recipeFavouriteImageView);
                }
            }else{
                Glide.with(context).load(R.drawable.unfilledstar).apply(options2).into(holder.recipeFavouriteImageView);
            }
        }else{
            Glide.with(context).load(R.drawable.unfilledstar).apply(options2).into(holder.recipeFavouriteImageView);
        }
    }
}
