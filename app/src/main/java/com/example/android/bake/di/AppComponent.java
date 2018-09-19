package com.example.android.bake.di;

import com.example.android.bake.App;
import com.example.android.bake.ui.IngredientsWidgetConfigActivity;
import com.example.android.bake.ui.RecipesActivity;
import com.example.android.bake.viewmodel.RecipesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponent {

    void inject(App app);

    void inject(RecipesActivity activity);
    void inject(IngredientsWidgetConfigActivity activity);

    void inject(RecipesViewModel recipesViewModel);
}
