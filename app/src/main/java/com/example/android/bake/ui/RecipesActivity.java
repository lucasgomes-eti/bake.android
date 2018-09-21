package com.example.android.bake.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bake.App;
import com.example.android.bake.R;
import com.example.android.bake.helper.AndroidExtensions;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.viewmodel.RecipesViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity implements ListItemClickListener {

    private final RecipesAdapter recipesAdapter = new RecipesAdapter(new ArrayList<>(), this);

    public static final String RECIPE_KEY = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ((App) getApplication()).component.inject(this);
        RecipesViewModel viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);

        RecyclerView.LayoutManager layoutManager;
        if (AndroidExtensions.getIsTablet(getApplicationContext())) {
            layoutManager = new GridLayoutManager(this, AndroidExtensions.responsiveNumberOfColumns(getApplicationContext()));
        } else {
            layoutManager = new LinearLayoutManager(this);
        }


        RecyclerView recyclerView = findViewById(R.id.rv_recipes);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipesAdapter);

        viewModel.getRecipes().observe(this, recipes -> {
            recipesAdapter.mRecipes.addAll(recipes);
            recipesAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Recipe recipe = recipesAdapter.mRecipes.get(itemIndex);

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);

        startActivity(intent);
    }
}
