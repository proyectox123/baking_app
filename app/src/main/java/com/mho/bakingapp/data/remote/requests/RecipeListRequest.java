package com.mho.bakingapp.data.remote.requests;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mho.bakingapp.data.remote.daos.RecipeDao;
import com.mho.bakingapp.data.remote.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListRequest {

    private MutableLiveData<List<Recipe>> recipeListData = new MutableLiveData<>();

    public MutableLiveData<List<Recipe>> getRecipeListData() {
        return recipeListData;
    }

    public void request(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RecipeDao recipeDao = retrofit.create(RecipeDao.class);
        Call<List<Recipe>> call = recipeDao.getRecipeList();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                switch (response.code()) {
                    case 200:
                        List<Recipe> data = response.body();
                        //view.notifyDataSetChanged(data.getResults());
                        recipeListData.setValue(data);
                        break;
                    case 401:
                        recipeListData.setValue(null);
                        break;
                    default:
                        recipeListData.setValue(null);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("error", t.toString());
                recipeListData.setValue(null);
            }
        });
    }

}