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

    private VM viewModel;

    public abstract int getIdLayout();

    public abstract int getBindingVariable();

    public abstract VM getViewModel();

    public abstract void setNavigator();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel = getViewModel();

        setNavigator();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, getIdLayout(), container, false);
        this.binding.setLifecycleOwner(this);
        this.binding.setVariable(getBindingVariable(), viewModel);
        this.binding.executePendingBindings();
        return this.binding.getRoot();
    }
}