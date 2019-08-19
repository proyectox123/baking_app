package com.mho.bakingapp.bases;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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