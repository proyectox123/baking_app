package com.mho.bakingapp.adapters.recipe;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.adapters.BaseViewHolder;

public class RecipeViewHolder extends BaseViewHolder<String> {

    //region Constants

    final static int layout = R.layout.item_recipe;

    //endregion

    //region Fields

    private TextView itemRecipeTitle;
    private TextView itemRecipeServings;

    private OnRecipeViewHolderListener onRecipeViewHolderListener;

    //endregion

    //region Constructors

    RecipeViewHolder(@NonNull View itemView,
                     OnRecipeViewHolderListener onRecipeViewHolderListener) {
        super(itemView);

        this.itemRecipeTitle = itemView.findViewById(R.id.itemRecipeTitle);
        this.itemRecipeServings = itemView.findViewById(R.id.itemRecipeServings);

        this.onRecipeViewHolderListener = onRecipeViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final String item) {
        itemRecipeTitle.setText(String.valueOf(getAdapterPosition()));
        itemRecipeServings.setText(String.valueOf(getAdapterPosition()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeViewHolderListener.selectRecipe(item);
            }
        });
    }

    //endregion

    //region Inner Classes & Interfaces

    public interface OnRecipeViewHolderListener{
        public void selectRecipe(String recipe);
    }

    //endregion
}