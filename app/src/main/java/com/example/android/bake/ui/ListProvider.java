package com.example.android.bake.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bake.R;
import com.example.android.bake.model.Ingredient;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<Ingredient> ingredients;
    private final Context context;

    public ListProvider(Context context, Intent intent) {
        this.context = context;

        Bundle bundle = intent.getBundleExtra(IngredientsAppWidget.INGREDIENTS_BUNDLE_KEY);

        ingredients = bundle.getParcelableArrayList(IngredientsAppWidget.INGREDIENTS_KEY);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.item_widget);
        Ingredient ingredient = ingredients.get(position);
        remoteView.setTextViewText(R.id.tv_item_widget, ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
