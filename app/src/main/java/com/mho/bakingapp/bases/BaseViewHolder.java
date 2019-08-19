package com.mho.bakingapp.bases;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    //region Fields

    protected Context context;

    //endregion

    //region Constructors

    protected BaseViewHolder(@NonNull View itemView) {
        super(itemView);

        this.context = itemView.getContext();
    }

    //endregion

    //region Abstract Methods

    public abstract void bind(T item);

    //endregion
}
