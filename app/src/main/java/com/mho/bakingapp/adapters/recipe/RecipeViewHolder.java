package com.mho.bakingapp.adapters.recipe;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewHolder;
import com.mho.bakingapp.data.remote.models.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeViewHolder extends BaseViewHolder<Recipe> {

    //region Constants

    final static int layout = R.layout.item_recipe;

    //endregion

    //region Fields

    private ImageView itemRecipeIcon;
    private TextView itemRecipeTitle;
    private TextView itemRecipeServings;
    private TextView itemRecipeIngredients;

    private OnRecipeViewHolderListener onRecipeViewHolderListener;

    //endregion

    //region Constructors

    RecipeViewHolder(@NonNull View itemView,
                     OnRecipeViewHolderListener onRecipeViewHolderListener) {
        super(itemView);

        this.itemRecipeIcon = itemView.findViewById(R.id.itemRecipeIcon);
        this.itemRecipeTitle = itemView.findViewById(R.id.itemRecipeTitle);
        this.itemRecipeServings = itemView.findViewById(R.id.itemRecipeServings);
        this.itemRecipeIngredients = itemView.findViewById(R.id.itemRecipeIngredients);

        this.onRecipeViewHolderListener = onRecipeViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Recipe item) {
        initRecipeName(item.getName());
        initRecipeServingsNumber(context.getString(R.string.item_recipe_servings, item.getServings()));
        initRecipeIngredientsNumber(context.getString(R.string.item_recipe_ingredients, item.getIngredientsCount()));
        initRecipeIcon(item.getImage());

        itemView.setOnClickListener(v -> onRecipeViewHolderListener.selectRecipe(item));
    }

    //endregion

    //region Private Methods

    private void initRecipeName(String recipeName){
        itemRecipeTitle.setText(recipeName);
    }

    private void initRecipeServingsNumber(String recipeServings){
        itemRecipeServings.setText(recipeServings);
    }

    private void initRecipeIngredientsNumber(String recipeIngredients){
        itemRecipeIngredients.setText(recipeIngredients);
    }

    private void initRecipeIcon(String image){
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.ic_tray)
                .error(R.drawable.ic_tray)
                .into(itemRecipeIcon);
    }

    //endregion

    //region Inner Classes & Interfaces

    public interface OnRecipeViewHolderListener{
        void selectRecipe(Recipe recipe);
    }

    //endregion
}