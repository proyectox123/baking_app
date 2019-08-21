package com.mho.bakingapp.widgets;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.preferences.RecipePreferences;
import com.mho.bakingapp.data.remote.models.Recipe;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    //region Fields

    private Context context;

    private Recipe recipe;

    //endregion

    //region Constructors

    ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onCreate() { }

    @Override
    public void onDataSetChanged() {
        recipe = RecipePreferences.loadRecipe(context);
    }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_recipe_ingredient);

        String ingredientLabel = "* " + recipe.getIngredients().get(position).getIngredient();
        row.setTextViewText(R.id.itemIngredientDetail, ingredientLabel);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //endregion
}