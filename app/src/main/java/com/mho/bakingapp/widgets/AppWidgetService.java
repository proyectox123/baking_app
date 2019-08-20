package com.mho.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.mho.bakingapp.data.preferences.RecipePreferences;
import com.mho.bakingapp.data.remote.models.Recipe;

public class AppWidgetService extends RemoteViewsService {

    //region Override Methods & Callbacks

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(getApplicationContext());
    }

    //endregion

    //region Public Methods

    public static void updateWidget(Context context, Recipe recipe) {
        RecipePreferences.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeListWidget.class));
        RecipeListWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    //endregion

}