package com.example.android.bake.helper;

import android.content.Context;
import android.util.DisplayMetrics;

public class AndroidExtensions {

    public static boolean getIsTablet(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.widthPixels / displayMetrics.density) >= 900;
    }

    public static int responsiveNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return  (int) (dpWidth / 180);
    }
}
