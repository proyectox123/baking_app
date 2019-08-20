package com.mho.bakingapp.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mho.bakingapp.bases.BasePreferences;
import com.mho.bakingapp.data.remote.models.Recipe;

public class RecipePreferences extends BasePreferences {

    //region Constants

    private final static String PREF_RECIPE = "PREF_RECIPE";

    //endregion

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();

        editor.putString(PREF_RECIPE, recipe == null ? null : new Gson().toJson(recipe));
        editor.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);

        String json = prefs.getString(PREF_RECIPE, null);
        return json == null ? null : new Gson().fromJson(json, Recipe.class);
    }

}