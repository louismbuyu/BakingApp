package com.example.louisnelsonlevoride.bakingapp.Services;

import com.example.louisnelsonlevoride.bakingapp.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeClient {

    @GET("baking.json")
    Call<List<Recipe>> recipes();


}
