package com.example.android.bake.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bake.App;
import com.example.android.bake.R;
import com.example.android.bake.model.Ingredient;
import com.example.android.bake.model.Recipe;
import com.example.android.bake.viewmodel.RecipesViewModel;

import java.util.ArrayList;

public class IngredientsWidgetConfigActivity extends AppCompatActivity implements ListItemClickListener {

    private final ChooseRecipeAdapter recipesAdapter = new ChooseRecipeAdapter(new ArrayList<>(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_widget_config);
        ((App) getApplication()).component.inject(this);
        RecipesViewModel viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

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

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        int appWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        IngredientsAppWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId, (ArrayList<Ingredient>) recipe.getIngredients(), recipe.getName());
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        setResult(Activity.RESULT_OK, resultValue);

        finish();
    }
}
