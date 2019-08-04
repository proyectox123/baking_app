package com.mho.bakingapp.adapters.recipe;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mho.bakingapp.adapters.BaseRecyclerViewAdapter;

public class RecipeListAdapter extends BaseRecyclerViewAdapter<String, RecipeViewHolder> {

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