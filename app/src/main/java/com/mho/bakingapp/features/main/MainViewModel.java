package com.mho.bakingapp.features.main;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.util.Log;

import com.mho.bakingapp.features.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.BUNDLE_RECIPE_LIST;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    //region Constants

    private final static String TAG = MainViewModel.class.getSimpleName();

    //endregion

    //region Fields

    private MutableLiveData<List<String>> recipeListData = new MutableLiveData<>();

    //endregion

    //region Private Methods

    MutableLiveData<List<String>> getRecipeListData() {
        return recipeListData;
    }

    void validatePaneMode(boolean twoPaneMode){
        Log.d(TAG, "validatePaneMode twoPaneMode " + twoPaneMode);
        if(twoPaneMode){
            getNavigator().setGridLayoutManager();
        }else {
            getNavigator().setLinearLayoutManager();
        }
    }

    void validateInstanceState(Bundle savedInstanceState){
        Log.d(TAG, "validateInstanceState");
        if (savedInstanceState == null) {
            Log.d(TAG, "validateInstanceState == null");
            initRecipeList();
            return;
        }

        //recipeList = savedInstanceState.getParcelableArrayList(BUNDLE_MOVIE_TOP_RATED_LIST);
        List<String> recipeList = savedInstanceState.getStringArrayList(BUNDLE_RECIPE_LIST);
        if(recipeList == null || recipeList.size() == 0){
            Log.d(TAG, "recipeList == null || size == 0");
            initRecipeList();
            return;
        }

        recipeListData.setValue(recipeList);
    }

    void saveInstanceState(Bundle outState) {
        Log.d(TAG, "saveInstanceState");

        List<String> recipeList = recipeListData.getValue();
        if(recipeList != null && recipeList.size() > 0){
            //outState.putParcelableArrayList(BUNDLE_RECIPE_LIST, new ArrayList<>(recipeList));
            outState.putStringArrayList(BUNDLE_RECIPE_LIST, new ArrayList<>(recipeList));
        }
    }

    private void initRecipeList(){
        Log.d(TAG, "initRecipeList");
        List<String> recipeList = new ArrayList<>();
        for(int i = 1; i<=30; i++){
            recipeList.add("Recipe Nº " + i);
        }

        recipeListData.setValue(recipeList);
    }

    //endregion
}