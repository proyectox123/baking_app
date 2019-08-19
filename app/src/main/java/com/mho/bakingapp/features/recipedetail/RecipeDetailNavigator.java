package com.mho.bakingapp.features.recipedetail;

import com.mho.bakingapp.data.remote.models.Ingredient;
import com.mho.bakingapp.data.remote.models.Step;

import java.util.List;

public interface RecipeDetailNavigator {
    void finishActivity();
    void updateIngredientList(List<Ingredient> ingredients);
    void updateStepList(List<Step> steps);
}