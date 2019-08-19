package com.mho.bakingapp.bases;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<D extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    public D binding;

    public abstract int getIdLayout();

    public abstract int getBindingVariable();

    public abstract VM getViewModel();

    public abstract void setNavigator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, getIdLayout());
        this.binding.setLifecycleOwner(this);
        VM viewModel = getViewModel();
        binding.setVariable(getBindingVariable(), viewModel);
        binding.executePendingBindings();
        setNavigator();
    }
}