package com.mho.bakingapp.features.recipestep;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

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

    //region Constructors

    public RecipeStepViewModel(@NonNull Application application) {
        super(application);
    }

    //endregion

    //Private Methods

    void validateRecipeStepArguments(Bundle arguments) {
        if (arguments == null) {
            getNavigator().finishActivity();
            return;
        }

        if(!arguments.containsKey(EXTRA_STEP_ID) || !arguments.containsKey(EXTRA_STEP_LIST)){
            getNavigator().finishActivity();
            return;
        }

        stepId = arguments.getInt(EXTRA_STEP_ID);
        stepList = arguments.getParcelableArrayList(EXTRA_STEP_LIST);
    }

    void initStepTabs() {
        getNavigator().updateStepTabs(stepId, stepList);
    }

    //endregion
}