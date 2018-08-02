package com.example.louisnelsonlevoride.bakingapp.Step;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;

public class StepActivity extends AppCompatActivity {
    Recipe recipe;
    private int position;
    DisplayMetrics outMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Step step = getIntent().getParcelableExtra("step_item");
        recipe = getIntent().getParcelableExtra("recipe_item");
        position = getIntent().getIntExtra("position", -1);
        Boolean stepType = getIntent().getBooleanExtra("step",false);

        int heightPixels = getIntent().getIntExtra("heightPixels",0);
        int widthPixels = getIntent().getIntExtra("widthPixels",0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.heightPixels = heightPixels;
        displayMetrics.widthPixels = widthPixels;

        if (stepType){
            if (step != null){
                StepFragment recipeFragment = new StepFragment(StepActivity.this,step, recipe, position, displayMetrics);

                fragmentManager.beginTransaction()
                        .add(R.id.detail_step_container_id, recipeFragment)
                        .commit();
            }
        }else{
            IngredientsFragment detailIngredientsFragment = new IngredientsFragment(StepActivity.this, recipe);
            fragmentManager.beginTransaction()
                    .add(R.id.detail_step_container_id, detailIngredientsFragment)
                    .commit();
        }
    }
}
