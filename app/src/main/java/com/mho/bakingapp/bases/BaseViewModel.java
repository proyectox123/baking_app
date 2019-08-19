package com.mho.bakingapp.bases;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends ViewModel {

    //region Fields

    private WeakReference<N> navigator;

    //endregion

    //region Public Methods

    public N getNavigator() {
        return navigator.get();
    }

    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }

    //endregion
}