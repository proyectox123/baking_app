package com.mho.bakingapp.bases;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<D extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    public D binding;

    public abstract int getIdLayout();

    public abstract int getBindingVariable();

    public abstract VM getViewModel();

    public abstract void setNavigator();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, getIdLayout(), container, false);
        this.binding.setLifecycleOwner(this);
        VM viewModel = getViewModel();
        this.binding.setVariable(getBindingVariable(), viewModel);
        this.binding.executePendingBindings();
        setNavigator();
        return this.binding.getRoot();
    }
}