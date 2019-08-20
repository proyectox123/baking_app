package com.mho.bakingapp.features.recipestep;

import com.mho.bakingapp.data.remote.models.Step;

import java.util.List;

interface RecipeStepNavigator {
    void finishActivity();
    void updateStepTabs(int currentPosition, List<Step> stepList);
}