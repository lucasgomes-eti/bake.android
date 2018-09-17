package com.example.android.bake.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.android.bake.App;
import com.example.android.bake.R;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.repository.RecipeRepository;

import java.util.List;

import javax.inject.Inject;

public class RecipesViewModel extends AndroidViewModel {

    @Inject
    public RecipeRepository recipeRepository;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        ((App) application).component.inject(this);
    }

    public List<Recipe> loadRecipes() {
        return recipeRepository.loadRecipes();
    }
}
