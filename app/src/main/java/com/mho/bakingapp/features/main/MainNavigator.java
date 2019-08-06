package com.mho.bakingapp.features.main;

import com.mho.bakingapp.data.remote.models.Recipe;

import java.util.List;

public interface MainNavigator {
    void setGridLayoutManager();
    void setLinearLayoutManager();
    void updateRecipeList(List<Recipe> recipeList);
    void showUpdateRecipeListError();
}