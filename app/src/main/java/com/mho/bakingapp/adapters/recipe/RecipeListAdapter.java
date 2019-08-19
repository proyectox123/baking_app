package com.mho.bakingapp.adapters.recipe;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import com.mho.bakingapp.bases.BaseRecyclerViewAdapter;
import com.mho.bakingapp.data.remote.models.Recipe;

public class RecipeListAdapter extends BaseRecyclerViewAdapter<Recipe, RecipeViewHolder> {

    //region Fields

    private final RecipeViewHolder.OnRecipeViewHolderListener onRecipeViewHolderListener;

    //endregion

    //region Constructors

    public RecipeListAdapter(RecipeViewHolder.OnRecipeViewHolderListener onRecipeViewHolderListener){
        this.onRecipeViewHolderListener = onRecipeViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected int getLayoutIdForListItem() {
        return RecipeViewHolder.layout;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecipeViewHolder(createItemView(viewGroup), onRecipeViewHolderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.bind(getItemByPosition(position));
    }

    //endregion
}