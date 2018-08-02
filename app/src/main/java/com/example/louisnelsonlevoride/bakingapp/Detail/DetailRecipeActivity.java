package com.example.louisnelsonlevoride.bakingapp.Detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;
import com.example.louisnelsonlevoride.bakingapp.Models.Step;
import com.example.louisnelsonlevoride.bakingapp.R;
import com.example.louisnelsonlevoride.bakingapp.Recipe.RecipeAdapter;
import com.example.louisnelsonlevoride.bakingapp.RotationViewModel;
import com.example.louisnelsonlevoride.bakingapp.Step.IngredientsFragment;
import com.example.louisnelsonlevoride.bakingapp.Step.StepActivity;
import com.example.louisnelsonlevoride.bakingapp.Step.StepFragment;

public class DetailRecipeActivity extends AppCompatActivity implements DetailRecipeOnClickListener, IngredientsOnClickListener {

    Recipe recipe;
    FragmentManager fragmentManager;
    private final static String TAG = "DetailRecipeActivity";
    private LinearLayout linearLayout;
    private Integer position;
    private Step step;
    private Boolean stepType;
    private int screen_height;
    private int screen_width;
    private DetailRecipeRotationViewModel detailRecipeRotationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        linearLayout = findViewById(R.id.two_pane_layout);
        detailRecipeRotationViewModel = ViewModelProviders.of(this).get(DetailRecipeRotationViewModel.class);

        if (detailRecipeRotationViewModel.recipe != null){
            recipe = detailRecipeRotationViewModel.recipe;
        }else{
            recipe = getIntent().getParcelableExtra("recipe_item");
        }

        setTitle(recipe.getName());
        fragmentManager = getSupportFragmentManager();

        RecipeFragment recipeFragment = new RecipeFragment(recipe, this, this, this);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container_id, recipeFragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailRecipeRotationViewModel.recipe = recipe;
    }

    private void setupDetailView(){

        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        screen_height = outMetrics.heightPixels;
        screen_width = outMetrics.widthPixels;

        if (linearLayout != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (stepType){
                if (step != null){
                    StepFragment stepRecipeFragment = new StepFragment(DetailRecipeActivity.this,step, recipe, position, outMetrics);

                    fragmentManager.beginTransaction()
                            .add(R.id.detail_step_container_id, stepRecipeFragment)
                            .commit();
                }
            }else{
                IngredientsFragment detailIngredientsFragment = new IngredientsFragment(DetailRecipeActivity.this, recipe);
                fragmentManager.beginTransaction()
                        .add(R.id.detail_step_container_id, detailIngredientsFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onStepClick(int position, Step step) {
        this.position = position;
        this.step = step;
        this.stepType = true;
        if (linearLayout != null) {
            setupDetailView();
        }else{
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("step_item", step);
            intent.putExtra("recipe_item", recipe);
            intent.putExtra("position", position);
            intent.putExtra("step", true);
            intent.putExtra("width", screen_width);
            intent.putExtra("height", screen_height);
            startActivity(intent);
        }
    }

    @Override
    public void onIngredientsClick() {
        this.stepType = false;
        if (linearLayout != null){
            setupDetailView();
        }else{
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("recipe_item", recipe);
            intent.putExtra("step", false);
            startActivity(intent);
        }
    }
}
