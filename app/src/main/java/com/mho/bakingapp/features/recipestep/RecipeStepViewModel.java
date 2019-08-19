package com.mho.bakingapp.features.recipestep;

import android.os.Bundle;

import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Step;

import java.util.List;

import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_ID;
import static com.mho.bakingapp.utils.Constants.EXTRA_STEP_LIST;

public class RecipeStepViewModel extends BaseViewModel<RecipeStepNavigator> {

    //region Fields

    private int stepId;
    private List<Step> stepList;

    //endregion

    //Private Methods

    void validateRecipeStepArguments(Bundle arguments) {
        /*
        if (savedInstanceState == null) {
      stepId = getArguments().getInt(RecipeStepActivity.EXTRA_STEP_ID);
    } else {
      stepId = savedInstanceState.getInt(RecipeStepActivity.EXTRA_STEP_ID);
    }
         */

        if (arguments == null) {
            getNavigator().finishActivity();
            return;
        }

        if(!arguments.containsKey(EXTRA_STEP_ID) || !arguments.containsKey(EXTRA_STEP_ID)){
            getNavigator().finishActivity();
            return;
        }

        stepId = arguments.getInt(EXTRA_STEP_ID);
        stepList = arguments.getParcelableArrayList(EXTRA_STEP_LIST);
    }

    void initStepTabs() {
        getNavigator().updateStepTabs(stepList);
    }

    //endregion
}