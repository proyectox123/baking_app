package com.mho.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.preferences.RecipePreferences;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.features.main.MainActivity;

public class RecipeListWidget extends AppWidgetProvider {

    //region Override Methods & Callbacks

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) { }

    @Override
    public void onDisabled(Context context) { }

    //endregion

    //region Public Methods

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        Recipe recipe = RecipePreferences.loadRecipe(context);
        if (recipe == null) {
            return;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_list_widget);

        views.setTextViewText(R.id.appwidget_text, recipe.getName());
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        Intent intent = new Intent(context, AppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.appwidget_list, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);
    }

    //endregion
}