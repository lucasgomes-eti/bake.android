package com.example.android.bake.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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

public class RecipesActivity extends AppCompatActivity implements ListItemClickListener {

    private RecipesViewModel viewModel;
    private RecipesAdapter recipesAdapter = new RecipesAdapter(new ArrayList<>(), this);
    private RecyclerView.LayoutManager layoutManager;

    public static String RECIPE_KEY = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ((App) getApplication()).component.inject(this);
        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);

        if (AndroidExtensions.getIsTablet(getApplicationContext())) {
            layoutManager = new GridLayoutManager(this, AndroidExtensions.responsiveNumberOfColumns(getApplicationContext()));
        } else {
            layoutManager = new LinearLayoutManager(this);
        }

        Log.d("nutella_pie", String.valueOf(R.drawable.nutella_pie));
        Log.d("brownies", String.valueOf(R.drawable.brownies));
        Log.d("yellow_cake", String.valueOf(R.drawable.yellow_cake));
        Log.d("cheesecake", String.valueOf(R.drawable.cheesecake));


        RecyclerView recyclerView = findViewById(R.id.rv_recipes);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipesAdapter);

        recipesAdapter.mRecipes.addAll(viewModel.loadRecipes());
        recipesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(int itemIndex) {
        Recipe recipe = recipesAdapter.mRecipes.get(itemIndex);

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);

        startActivity(intent);
    }
}
