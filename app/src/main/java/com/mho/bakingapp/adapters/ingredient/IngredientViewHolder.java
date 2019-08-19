package com.mho.bakingapp.adapters.ingredient;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewHolder;
import com.mho.bakingapp.data.remote.models.Ingredient;

public class IngredientViewHolder extends BaseViewHolder<Ingredient> {

    //region Constants

    final static int layout = R.layout.item_recipe_ingredient;

    //endregion

    //region Fields

    private TextView itemIngredientDetail;

    //endregion

    //region Constructors

    IngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemIngredientDetail = itemView.findViewById(R.id.itemIngredientDetail);
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Ingredient item) {
        String ingredientLabel = "* " + item.getIngredient() + "(" + item.getQuantity() + " " +item.getMeasure() + ")";

        itemIngredientDetail.setText(ingredientLabel);
    }

    //endregion

    //region Inner Classes & Interfaces

    //endregion
}