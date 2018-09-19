package com.example.android.bake.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.android.bake.ui.ListProvider;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(getApplicationContext(), intent));
    }
}