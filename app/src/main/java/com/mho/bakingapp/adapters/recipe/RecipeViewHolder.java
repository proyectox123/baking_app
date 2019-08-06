package com.mho.bakingapp.adapters.recipe;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewHolder;
import com.mho.bakingapp.data.remote.models.Recipe;

public class RecipeViewHolder extends BaseViewHolder<Recipe> {

    //region Constants

    final static int layout = R.layout.item_recipe;

    //endregion

    //region Fields

    private TextView itemRecipeTitle;
    private TextView itemRecipeServings;
    private TextView itemRecipeIngredients;

    private OnRecipeViewHolderListener onRecipeViewHolderListener;

    //endregion

    //region Constructors

    RecipeViewHolder(@NonNull View itemView,
                     OnRecipeViewHolderListener onRecipeViewHolderListener) {
        super(itemView);

        this.itemRecipeTitle = itemView.findViewById(R.id.itemRecipeTitle);
        this.itemRecipeServings = itemView.findViewById(R.id.itemRecipeServings);
        this.itemRecipeIngredients = itemView.findViewById(R.id.itemRecipeIngredients);

        this.onRecipeViewHolderListener = onRecipeViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Recipe item) {
        String recipeServingsLabel = context.getString(R.string.item_recipe_servings, item.getServings());
        String recipeIngredientsLabel = context.getString(R.string.item_recipe_ingredients, item.getIngredientsCount());

        itemRecipeTitle.setText(item.getName());
        itemRecipeServings.setText(recipeServingsLabel);
        itemRecipeIngredients.setText(recipeIngredientsLabel);

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
        void selectRecipe(Recipe recipe);
    }

    //endregion
}