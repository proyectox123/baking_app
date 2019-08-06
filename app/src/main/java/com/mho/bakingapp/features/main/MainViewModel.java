package com.mho.bakingapp.features.main;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.data.remote.requests.RecipeListRequest;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.BUNDLE_RECIPE_LIST;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    //region Constants

    private final static String TAG = MainViewModel.class.getSimpleName();

    //endregion

    //region Fields

    private RecipeListRequest recipeListRequest;

    //endregion

    //region Constructors

    public MainViewModel(){
        recipeListRequest = new RecipeListRequest();
    }

    //endregion

    //region Private Methods

    MutableLiveData<List<Recipe>> getRecipeListData() {
        return recipeListRequest.getRecipeListData();
    }

    void validatePaneMode(boolean twoPaneMode){
        Log.d(TAG, "validatePaneMode twoPaneMode " + twoPaneMode);
        if(twoPaneMode){
            getNavigator().setGridLayoutManager();
        }else {
            getNavigator().setLinearLayoutManager();
        }
    }

    void validateInstanceState(@Nullable Bundle savedInstanceState){
        Log.d(TAG, "validateInstanceState");
        if (savedInstanceState == null) {
            Log.d(TAG, "validateInstanceState == null");
            initRecipeList();
            return;
        }

        List<Recipe> recipeList = savedInstanceState.getParcelableArrayList(BUNDLE_RECIPE_LIST);
        if(recipeList == null || recipeList.size() == 0){
            Log.d(TAG, "recipeList == null || size == 0");
            initRecipeList();
            return;
        }

        getRecipeListData().setValue(recipeList);
    }

    void saveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "saveInstanceState");

        List<Recipe> recipeList = getRecipeListData().getValue();
        if(recipeList != null && recipeList.size() > 0){
            outState.putParcelableArrayList(BUNDLE_RECIPE_LIST, new ArrayList<>(recipeList));
        }
    }

    void validateRecipeList(@Nullable List<Recipe> recipeList){
        Log.d(TAG, "validateRecipeList recipeList: " + recipeList);
        if(recipeList == null || recipeList.size() == 0){
            getNavigator().showUpdateRecipeListError();
            return;
        }

        getNavigator().updateRecipeList(recipeList);
    }

    private void initRecipeList(){
        Log.d(TAG, "initRecipeList");
        recipeListRequest.request();
    }

    //endregion
}