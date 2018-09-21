package com.example.android.bake.remote;

import com.example.android.bake.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesService {

    @GET("recipes")
    Call<List<Recipe>> getRecipes();
}
