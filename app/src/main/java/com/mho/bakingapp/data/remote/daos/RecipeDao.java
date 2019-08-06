package com.mho.bakingapp.data.remote.daos;

import com.mho.bakingapp.data.remote.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeDao {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipeList();
}