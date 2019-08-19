package com.mho.bakingapp.adapters.step;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.mho.bakingapp.bases.BaseRecyclerViewAdapter;
import com.mho.bakingapp.data.remote.models.Step;

public class StepListAdapter extends BaseRecyclerViewAdapter<Step, StepViewHolder> {

    //region Fields

    private StepViewHolder.OnStepViewHolderListener onStepViewHolderListener;

    //endregion

    //region Constructors

    public StepListAdapter(StepViewHolder.OnStepViewHolderListener onStepViewHolderListener){
        this.onStepViewHolderListener = onStepViewHolderListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected int getLayoutIdForListItem() {
        return StepViewHolder.layout;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StepViewHolder(createItemView(viewGroup), onStepViewHolderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder viewHolder, int position) {
        viewHolder.bind(getItemByPosition(position));
    }

    //endregion
}