package com.example.android.bake.di;

import android.content.Context;

import com.example.android.bake.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App mApp;

    public AppModule(App app){
        mApp = app;
    }

    @Provides
    @Singleton
    App providesApp() {
        return mApp;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApp.getApplicationContext();
    }
}
