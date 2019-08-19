package com.mho.bakingapp.adapters.step;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseViewHolder;
import com.mho.bakingapp.data.remote.models.Step;

public class StepViewHolder extends BaseViewHolder<Step> {

    //region Constants

    final static int layout = R.layout.item_recipe_step;

    //endregion

    //region Fields

    private TextView itemStepDetail;

    private OnStepViewHolderListener onStepViewHolderListener;

    //endregion

    //region Constructors

    StepViewHolder(@NonNull View itemView, OnStepViewHolderListener onStepViewHolderListener) {
        super(itemView);

        this.itemStepDetail = itemView.findViewById(R.id.itemStepDetail);

        this.onStepViewHolderListener = onStepViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Step item) {
        String ingredientLabel = item.getId() + ". " + item.getShortDescription();

        itemStepDetail.setText(ingredientLabel);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStepViewHolderListener.selectStep(item);
            }
        });
    }

    //endregion

    //region Inner Classes & Interfaces

    //region Inner Classes & Interfaces

    public interface OnStepViewHolderListener{
        void selectStep(Step step);
    }

    //endregion

    //endregion
}