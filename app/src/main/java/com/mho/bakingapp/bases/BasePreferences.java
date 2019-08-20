package com.mho.bakingapp.bases;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class BasePreferences {

    private static final String PREFS_NAME = "baking_app_preferences";

    protected static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}