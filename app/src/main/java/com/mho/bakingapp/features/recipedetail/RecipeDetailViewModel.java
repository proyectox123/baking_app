package com.mho.bakingapp.features.recipedetail;

import android.os.Bundle;

import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.data.remote.models.Step;

import static com.mho.bakingapp.utils.Constants.EXTRA_RECIPE;

public class RecipeDetailViewModel extends BaseViewModel<RecipeDetailNavigator> {

    //region Fields

    private Recipe recipe;

    //endregion

    //region Private Methods

    void validateRecipeDetailArguments(Bundle arguments) {
        if (arguments == null) {
            getNavigator().finishActivity();
            return;
        }

        if(!arguments.containsKey(EXTRA_RECIPE)){
            getNavigator().finishActivity();
            return;
        }

        recipe = arguments.getParcelable(EXTRA_RECIPE);
    }

    void initRecipeIngredientList() {
        if (recipe == null) {
            return;
        }

        getNavigator().updateIngredientList(recipe.getIngredients());
    }

    void initRecipeStepList() {
        if (recipe == null) {
            return;
        }

        getNavigator().updateStepList(recipe.getSteps());
    }

    void validateSelectedStep(Step step) {
        getNavigator().startRecipeStep(step.getId(), recipe.getSteps());
    }

    //endregion

}