package com.example.android.bake.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.android.bake.R;
import com.example.android.bake.model.Ingredient;
import com.example.android.bake.service.WidgetService;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsAppWidget extends AppWidgetProvider {

    public static String INGREDIENTS_KEY = "ingredients";
    public static String INGREDIENTS_BUNDLE_KEY = "ingredientsBundle";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, ArrayList<Ingredient> ingredients, String recipeName) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_app_widget);

        views.setTextViewText(R.id.tv_title, context.getString(R.string.ingredients));
        views.setTextViewText(R.id.tv_recipe, recipeName);

        Intent intent = new Intent(context, WidgetService.class);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS_KEY, ingredients);

        intent.putExtra(INGREDIENTS_BUNDLE_KEY, bundle);


        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.lv_ingredients, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}