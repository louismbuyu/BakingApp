package com.example.louisnelsonlevoride.bakingapp;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProvider;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.louisnelsonlevoride.bakingapp.IdlingResource.EspressoIdlingResource;
import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeAdapter;
import com.example.louisnelsonlevoride.bakingapp.Services.RecipeClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.arch.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements DialogInterface {

    List<Recipe> recipeList;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    private final static String TAG = "MainActivity";
    private GridLayoutManager gridLayoutManager;
    private RotationViewModel rotationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Recipes");
        recyclerView = findViewById(R.id.recipe_recycleView);
        rotationViewModel = ViewModelProviders.of(this).get(RotationViewModel.class);

        if (rotationViewModel.recipes != null){
            gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
            recyclerView.setLayoutManager(gridLayoutManager);
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            recyclerView.setHasFixedSize(true);
            recipeAdapter = new RecipeAdapter(MainActivity.this, rotationViewModel.recipes, MainActivity.this);
            recyclerView.setAdapter(recipeAdapter);
        }else{
            getRecipes();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecipeAdapter recipeAdapter1 = (RecipeAdapter) recyclerView.getAdapter();
        rotationViewModel.recipes = recipeAdapter1.getRecipes();
    }

    public void getRecipes(){
        EspressoIdlingResource.increment();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RecipeClient client = retrofit.create(RecipeClient.class);

        Call<List<Recipe>> call = client.recipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
                //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setHasFixedSize(true);
                recipeAdapter = new RecipeAdapter(MainActivity.this, recipes, MainActivity.this);
                recyclerView.setAdapter(recipeAdapter);
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i(TAG, "Error: "+ t.toString());
                Toast.makeText(MainActivity.this, "Problem: "+t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialog(String recipeName){
        final SuccessDialog alert = new SuccessDialog();
        alert.showDialog(this, "You have successfully saved "+recipeName+" as your favourite recipe!");
        alert.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dialog.dismiss();
                getRecipes();
                updateWidget();
            }
        });
    }

    public void updateWidget(){
        Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}
