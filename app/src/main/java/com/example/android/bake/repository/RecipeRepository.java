package com.example.android.bake.repository;

import com.example.android.bake.App;
import com.example.android.bake.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecipeRepository {
    private final App app;

    @Inject
    public RecipeRepository(App app) {
        this.app = app;
    }

    public List<Recipe> loadRecipes() {

        String recipesJson = "";

        try {
            InputStream inputStream = app.getApplicationContext().getAssets().open("recipes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            inputStream.read(buffer);
            inputStream.close();
            recipesJson = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Recipe> recipes = new Gson().fromJson(recipesJson, new TypeToken<List<Recipe>>() {}.getType());

        if (recipes != null) {
            return recipes;
        } else {
            return new ArrayList<>();
        }
    }
}
