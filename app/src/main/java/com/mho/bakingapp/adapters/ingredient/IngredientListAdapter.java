package com.mho.bakingapp.adapters.ingredient;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import com.mho.bakingapp.bases.BaseRecyclerViewAdapter;
import com.mho.bakingapp.data.remote.models.Ingredient;

public class IngredientListAdapter extends BaseRecyclerViewAdapter<Ingredient, IngredientViewHolder> {

    //region Override Methods & Callbacks

    @Override
    protected int getLayoutIdForListItem() {
        return IngredientViewHolder.layout;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IngredientViewHolder(createItemView(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder viewHolder, int position) {
        viewHolder.bind(getItemByPosition(position));
    }

    //endregion
}