package com.example.android.bake.repository;

import android.support.annotation.WorkerThread;

import com.example.android.bake.App;
import com.example.android.bake.AppExecutors;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.remote.RecipesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class RecipeRepository {
    private final App app;
    private final RecipesService recipesService;

    @Inject
    public RecipeRepository(App app, RecipesService recipesService) {

        this.app = app;
        this.recipesService = recipesService;
    }

    @WorkerThread
    public List<Recipe> loadRecipes() {

        List<Recipe> result = new ArrayList<>();

        try {
            Response<List<Recipe>> response = recipesService.getRecipes().execute();

            if (response.isSuccessful() &&  response.body() != null) {
                result.addAll(response.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
