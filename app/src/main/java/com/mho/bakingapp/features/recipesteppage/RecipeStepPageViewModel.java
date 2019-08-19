package com.mho.bakingapp.features.recipesteppage;

import androidx.lifecycle.MutableLiveData;
import android.os.Bundle;

import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Step;

import static com.mho.bakingapp.utils.Constants.EXTRA_STEP;

public class RecipeStepPageViewModel extends BaseViewModel<RecipeStepPageNavigator> {

    //region Fields

    public final MutableLiveData<String> recipeStepDescription = new MutableLiveData<>();

    private Step step;

    //endregion

    //region Private Methods

    void validateRecipeStepPageArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if(!arguments.containsKey(EXTRA_STEP)){
            return;
        }

        step = arguments.getParcelable(EXTRA_STEP);
    }

    void initRecipeStep() {
        recipeStepDescription.setValue(step.getDescription());
    }

    //endregion
}