package com.example.android.bake;

import android.app.Application;

import com.example.android.bake.di.AppComponent;
import com.example.android.bake.di.AppModule;
import com.example.android.bake.di.DaggerAppComponent;

public class App extends Application {
    public final AppComponent component =
            DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();

    @Override
    public void onCreate() {
        super.onCreate();
        component.inject(this);
    }
}
