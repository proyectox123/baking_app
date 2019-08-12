package com.mho.bakingapp.adapters.step;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewHolder;
import com.mho.bakingapp.data.remote.models.Ingredient;
import com.mho.bakingapp.data.remote.models.Step;

public class StepViewHolder extends BaseViewHolder<Step> {

    //region Constants

    final static int layout = R.layout.item_recipe_step;

    //endregion

    //region Fields

    private TextView itemStepDetail;

    //endregion

    //region Constructors

    StepViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemStepDetail = itemView.findViewById(R.id.itemStepDetail);
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Step item) {
        String ingredientLabel = item.getId() + ". " + item.getShortDescription();

        itemStepDetail.setText(ingredientLabel);
    }

    //endregion

    //region Inner Classes & Interfaces

    //endregion
}