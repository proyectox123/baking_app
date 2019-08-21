package com.mho.bakingapp.bases;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public abstract class BaseViewModel<N> extends AndroidViewModel {

    //region Fields

    private WeakReference<N> navigator;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

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