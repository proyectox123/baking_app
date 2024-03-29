package com.mho.bakingapp.features.main;

import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.mho.bakingapp.bases.BaseViewModel;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.mho.bakingapp.data.remote.requests.RecipeListRequest;
import com.mho.bakingapp.features.recipestepvideo.RecipeStepVideo;
import com.mho.bakingapp.utils.FetchingIdlingResource;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.BUNDLE_RECIPE_LIST;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    //region Constants

    private final static String TAG = MainViewModel.class.getSimpleName();

    //endregion

    //region Fields

    public final MutableLiveData<Boolean> hasLoadError = new MutableLiveData<>();
    public final MutableLiveData<Boolean> hasInformation = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private RecipeListRequest recipeListRequest;

    //endregion

    //region Constructors

    //region Constructors

    public MainViewModel(@NonNull Application application) {
        super(application);

        this.recipeListRequest = new RecipeListRequest();
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
        hasInformation.setValue(false);
        hasLoadError.setValue(false);
        isLoading.setValue(false);

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
        isLoading.setValue(false);
        if(recipeList == null || recipeList.size() == 0){
            hasInformation.setValue(false);
            hasLoadError.setValue(true);
            getNavigator().showLoadingRecipeListError();
            return;
        }

        hasInformation.setValue(true);
        hasLoadError.setValue(false);
        getNavigator().updateRecipeList(recipeList);
    }

    void retryInitRecipeList(){
        getNavigator().beginFetching();
        recipeListRequest.request();
    }

    private void initRecipeList(){
        Log.d(TAG, "initRecipeList");
        isLoading.setValue(true);
        getNavigator().beginFetching();
        recipeListRequest.request();
    }

    //endregion
}